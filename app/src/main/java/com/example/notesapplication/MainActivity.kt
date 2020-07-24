package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapplication.Adapters.NotesAdapter
import com.example.notesapplication.Adapters.onNoteClick
import com.example.notesapplication.Models.Notes
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import io.realm.Realm
import io.realm.RealmResults


class MainActivity : AppCompatActivity(), onNoteClick {


    private lateinit var rv: RecyclerView
    private lateinit var fab: FloatingActionButton

    //Local List
    private lateinit var notesList: ArrayList<Notes>
    private lateinit var realm: Realm
    private lateinit var adapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(this)

        //Init
        rv = findViewById(R.id.rv_notes)
        fab = findViewById(R.id.fab_addNote)
        notesList = ArrayList<Notes>()
        realm = Realm.getDefaultInstance()


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

}
