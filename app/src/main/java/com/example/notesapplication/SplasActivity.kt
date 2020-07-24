package com.example.notesapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor

class SplasActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var handler: Handler
    val TAG = SplasActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas)
        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()

        Log.e(TAG, auth.currentUser?.uid.toString() + auth.currentUser.toString())

        handler = Handler()
        handler.postDelayed({


            if (auth.currentUser?.uid == null) {
                startActivity(intentFor<SignInActivity>())
            } else {
                startActivity(intentFor<MainActivity>())
            }

            finish()

        }, 1000)     //1 seconds delay

    }
}


