package com.example.notesapplication

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.notesapplication.databinding.ActivitySignInBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivitySignInBinding
    val TAG = SignInActivity::class.java.simpleName
    lateinit var builder: AlertDialog.Builder
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

        binding.apply {

            tvResendEmailVerification.setOnClickListener {
                resendEmailVerification()
            }

            tvForgotPassword.setOnClickListener {
                resetPassword()
            }

        }

    }

    private fun resetPassword() {
        builder = AlertDialog.Builder(this)
        builder.apply {
            title = "Forgot Password"

            val v = layoutInflater.inflate(R.layout.forgota_dialog, null)
            val x = v.findViewById<TextInputEditText>(R.id.et_fp)
            val un = v.findViewById<TextInputEditText>(R.id.et_fp).text.toString()
            Log.e(TAG, un + x.toString() + " \n" + x.text.toString())
            setView(v)

            setPositiveButton(
                "Reset"
            ) { _: DialogInterface, _: Int ->
                forgotPassword(un)
            }
            setNegativeButton("Cancel") { _, _ ->
            }
            setCancelable(true)
            create()
            show()
        }

    }

    private fun forgotPassword(un: String) {
        //Validate and enter the email str

        Log.e("Un", un)
        //Use UN

        val e = "Kavya24goyal@gmail.com"
        auth.sendPasswordResetEmail(e)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                    builder.setOnDismissListener { }
                } else {

                    Log.e(TAG, "Failed")
                }
            }

    }

    private fun resendEmailVerification() {


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
