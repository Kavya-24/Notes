package com.example.notesapplication.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapplication.Models.Notes
import com.example.notesapplication.R
import io.realm.RealmResults

class NotesAdapter(
    private val context: Context,
    private val notesList: RealmResults<Notes>,
    val onNoteClick: onNoteClick
) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_note, parent, false)
        return MyViewHolder(v, context, onNoteClick)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }


    class MyViewHolder(itemView: View, context: Context, onNoteClickListener: onNoteClick) :
        RecyclerView.ViewHolder(itemView) {

        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val id = itemView.findViewById<TextView>(R.id.tv_id)
        val desc = itemView.findViewById<TextView>(R.id.tv_description)

        fun bindPost(_note: Notes, clickLister: onNoteClick) {
            title.text = _note.title
            id.text = _note.id.toString()
            desc.text = _note.description


            //On Click
            itemView.setOnClickListener {
                clickLister.openNote(_note)
            }


        }


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        //Notes list is the result
        notesList.get(position)?.let { holder.bindPost(it, onNoteClick) }


    }
}

interface onNoteClick {
    fun openNote(note: Notes)
}