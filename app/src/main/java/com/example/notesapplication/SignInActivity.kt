package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notesapplication.databinding.ActivitySignInBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()


        binding.tvSignUp.setOnClickListener {
            goToRegister()
        }


    }

    private fun goToRegister() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(currentUser: FirebaseUser?) {

    }


}
