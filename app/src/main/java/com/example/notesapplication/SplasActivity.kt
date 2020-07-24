package com.example.notesapplication

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.intentFor

class SplasActivity : AppCompatActivity() {

    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas)

        handler = Handler()
        handler.postDelayed({


            if (FirebaseAuth.getInstance().currentUser == null) {
                startActivity(intentFor<SignInActivity>())
            } else {
                startActivity(intentFor<MainActivity>())
            }

            finish()

        }, 1000)     //1 seconds delay

    }
}


