package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.Adapters.NotesAdapter
import com.example.notesapplication.Adapters.onNoteClick
import com.example.notesapplication.Models.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.realm.Realm
import io.realm.RealmResults


class MainActivity : AppCompatActivity(), onNoteClick {


    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton

    //Local List
    private lateinit var notesList: ArrayList<Notes>
    private lateinit var realm: Realm
    private lateinit var adapter: NotesAdapter
    private lateinit var auth: FirebaseAuth

    val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Init
        rv = findViewById(R.id.rv_notes)
        fab = findViewById(R.id.fab_addNote)
        notesList = ArrayList<Notes>()
        realm = Realm.getDefaultInstance()
        auth = FirebaseAuth.getInstance()

        fab.setOnClickListener {
            val i = Intent(this, AddNote::class.java)
            startActivity(i)

        }

        rv.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        getAllNotes()


    }

    private fun getAllNotes() {
        //Clear


        notesList.clear()
        val results: RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()
        adapter = NotesAdapter(this, results, this)
        rv.adapter = adapter
        rv.adapter!!.notifyDataSetChanged()


    }

    override fun openNote(note: Notes) {

        val i = Intent(this, NoteView::class.java)

        val b = bundleOf(
            "id" to note.id,
            "title" to note.title,
            "desc" to note.description,
            "date" to note.timeStamp.toString()
        )

        i.putExtra("bundle", b)
        startActivity(i)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    //Menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        menuInflater.inflate(R.menu.top_menu, menu)

        return true

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_logout -> logout()
            R.id.menu_changePassword -> changePassword()


            else ->
                return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun changePassword() {
        //Reauthenticate the user
        reAuthenticate()
    }

    private fun reAuthenticate() {
        val user = auth.currentUser!!

        val credential = EmailAuthProvider
            .getCredential("user@example.com", "password1234")

        user.reauthenticate(credential)
            .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()
        goToLoginActity()
        finishAffinity()
    }

    private fun goToLoginActity() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)

    }


}
