package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapplication.Models.Notes
import io.realm.Realm
import io.realm.RealmResults
import kotlin.properties.Delegates

class NoteView : AppCompatActivity() {


    private lateinit var title: TextView
    private lateinit var id: TextView
    private lateinit var description: TextView
    private lateinit var timestamp: TextView

    private lateinit var mDelete: TextView
    private lateinit var mShare: TextView
    private lateinit var arg: Bundle
    private var noteId by Delegates.notNull<Int>()
    private lateinit var noteTitle: String
    private lateinit var noteDesc: String
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_view)

        //Init
        title = findViewById(R.id.tv_title)
        description = findViewById(R.id.tv_description)
        id = findViewById(R.id.tv_id)
        mDelete = findViewById(R.id.tv_delete)
        mShare = findViewById(R.id.tv_share)
        timestamp = findViewById(R.id.tv_date)

        arg = intent?.getBundleExtra("bundle")!!
        realm = Realm.getDefaultInstance()
        title.text = arg.getString("title")
        id.text = arg.getInt("id").toString()
        description.text = arg.getString("desc")
        timestamp.text = arg.getString("date")


        noteId = arg.getInt("id").toInt()
        noteTitle = arg.getString("title")!!
        noteDesc = arg.getString("desc")!!


        //Chcek value of either aved from database
        mDelete.setOnClickListener {
            deleteNote()
        }

        mShare.setOnClickListener {
            shareNote()
        }


    }


    private fun shareNote() {


        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, getString(R.string.share_format, noteTitle, noteDesc))
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    private fun deleteNote() {

        //Delete the note with the id
        //Find all the queries
        val results: RealmResults<Notes> = realm.where<Notes>(Notes::class.java).findAll()

        //Find the notre from all the results
        val noteTbDeleted: Notes? = results.where().equalTo("id", noteId).findFirst()

        if (noteTbDeleted != null) {
            if (!realm.isInTransaction) {
                realm.beginTransaction()
            }

            noteTbDeleted.deleteFromRealm()

            realm.commitTransaction()

            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }


    }


}

