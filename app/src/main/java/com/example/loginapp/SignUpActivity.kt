package com.example.loginapp

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        auth = Firebase.auth
        progressBar = findViewById(R.id.progressBar)

        val signUpButton: Button = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            // Handle sign up logic here
            progressBar.visibility = View.VISIBLE
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (isEmailValid(email) && isPasswordValid(password)) {
                // Perform user registration
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        // Email verification link sent successfully
                                        // You can show a message to the user to check their email
                                        Toast.makeText(
                                            this,
                                            "Registration email sent! Verify your email and then go to Login.",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    } else {
                                        // Email verification link sending failed
                                        // You can show an error message to the user
                                        Toast.makeText(
                                            baseContext,
                                            "Authentication failed. Retry!",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed. Retry!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }


            } else {
                // Show error messages for invalid email or password
                if (!isEmailValid(email)) {
                    emailEditText.error = "Invalid email address"
                }
                if (!isPasswordValid(password)) {
                    passwordEditText.error =
                        "Password must contain at least 6 characters including a numeral"
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()

        // Your code to handle what happens when the activity enters the onStop state
        // For example, you might release resources or pause ongoing tasks.
        finish()
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}\$"
        return password.matches(Regex(regex))
    }
}
