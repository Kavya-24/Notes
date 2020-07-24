package com.example.notesapplication.Utils

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import io.realm.Realm
import io.realm.RealmConfiguration

class MyApp : Application() {


    companion object {

        lateinit var instance: MyApp


        fun getApplication(): MyApp {
            return instance
        }

        fun getContext(): Context {
            return instance.applicationContext
        }
    }


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

        //For getting the application context
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}



