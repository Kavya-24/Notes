package com.example.notesapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notesapplication.databinding.ActivitySignUpBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignUpBinding
    val TAG = SignInActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        binding.tvLogin.setOnClickListener {
            goToLoginActivity()
        }


        binding.accountSignUp.setOnClickListener {
            signUp()
        }


    }

    private fun signUp() {
        if (validate()) {
            createUser()
        }


    }

    private fun createUser() {

        auth.createUserWithEmailAndPassword(
            binding.emailSignUp.text.toString(),
            binding.passwordSignUp.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Log.e(TAG, "createUserWithEmail:success")

                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Log.e(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }


            }


    }

    private fun updateUI(user: FirebaseUser?) {

        //Send UID
        Log.e(TAG, "USerId" + user?.uid.toString())
        Toast.makeText(this, "SignUp successful", Toast.LENGTH_SHORT).show()
        goToLoginActivity()
    }

    private fun goToLoginActivity() {
        val i = Intent(this, SignInActivity::class.java)
        startActivity(i)
        finish()

    }

    private fun validate(): Boolean {

        var isValid = true
        if (binding.emailSignUp.text.isNullOrEmpty()) {
            isValid = false
            binding.emailSignUp.error = "Email can not be empty"
        } else {
            isValid = true
        }

        if (binding.passwordSignUp.text.isNullOrEmpty()) {
            isValid = false
            binding.passwordSignUp.error = "Password can not be empty"
        } else {
            isValid = true
        }

        return isValid
    }
}
