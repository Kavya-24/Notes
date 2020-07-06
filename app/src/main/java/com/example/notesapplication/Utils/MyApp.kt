package com.example.notesapplication.Utils

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {


    override fun onCreate() {
        super.onCreate()

        //Initailze RealM
        Realm.init(this)

        //Create configuration
        val configurationNotes = RealmConfiguration.Builder()
            .name("Notes.db")
            .deleteRealmIfMigrationNeeded()
            .schemaVersion(0)
            .build()


        Realm.setDefaultConfiguration(configurationNotes)


    }


}