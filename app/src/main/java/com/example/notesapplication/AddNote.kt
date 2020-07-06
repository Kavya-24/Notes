package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesapplication.Models.Notes
import com.google.android.material.button.MaterialButton
import io.realm.Realm
import java.util.*

class AddNote : AppCompatActivity() {


    private lateinit var et: EditText
    private lateinit var title: AutoCompleteTextView
    private lateinit var mSave: MaterialButton
    private lateinit var mReset: MaterialButton
    lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        realm = Realm.getDefaultInstance()
        et = findViewById(R.id.et_description)
        title = findViewById(R.id.actv_title)
        mSave = findViewById(R.id.mbtn_save)
        mReset = findViewById(R.id.mbtn_reset)


        mSave.setOnClickListener {
            addNote()
        }

        mReset.setOnClickListener {
            et.text = null
            title.text = null
        }


    }

    private fun addNote() {

        //Use try and catch
        try {
            //Begin
            realm.beginTransaction()
            //This is for editing or adding or things like that

            val currentIdNumber: Number? = realm.where(Notes::class.java).max("id")
            val nextId = if (currentIdNumber == null) {
                1
            } else {
                currentIdNumber.toInt() + 1
            }

            //Get the current date and time
            val currentTime: Date = Calendar.getInstance().time


            //Create the notes object
            val note = Notes(nextId, title.text.toString(), et.text.toString(), currentTime)
            //Log this
            Log.e("Note", note.toString())

            //Update to realm
            realm.copyToRealmOrUpdate(note)
            realm.commitTransaction()

            Toast.makeText(this, "Note created successfully", Toast.LENGTH_SHORT).show()
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()


        } catch (e: Exception) {
            //Log it
            Log.e("Note", "Exception" + e.toString())
        }


    }

}
