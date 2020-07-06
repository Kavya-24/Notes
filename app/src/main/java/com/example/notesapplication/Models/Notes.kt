package com.example.notesapplication.Models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*


//Use open for this
open class Notes(

    //Id is the primary key
    @PrimaryKey
    var id: Int? = null,
    var title: String? = null,
    var description: String? = null,
    var timeStamp: Date? = null

) : RealmObject()