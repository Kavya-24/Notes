package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notesapplication.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignInBinding
    val TAG = SignInActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e(TAG, "In Login Activty")
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        auth = FirebaseAuth.getInstance()


        binding.tvSignUp.setOnClickListener {
            goToRegister()
        }

        binding.accountSignIn.setOnClickListener {

            Log.e(TAG, "In Login account SignInButton")
            login()
        }

    }

    private fun login() {

        auth.signInWithEmailAndPassword(
            binding.emailSignIn.text.toString(),
            binding.passwordSignIn.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()


                }


            }

    }

    private fun goToRegister() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }


    private fun updateUI(currentUser: FirebaseUser?) {

        Log.e(TAG, "USerId" + currentUser?.uid.toString())
        goToMainActivity()

    }

    private fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finishAffinity()
    }


}
