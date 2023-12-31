package com.example.loginapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        progressBar = findViewById(R.id.progressBar)

        val loginButton: Button = findViewById(R.id.loginButton)
        loginButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (isEmailValid(email) && isPasswordValid(password)) {
                // Perform authentication
                // If authentication is successful, navigate to the next screen
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        progressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val user = auth.currentUser
                            if (user != null && user.isEmailVerified) {
                                // User is authenticated and email is verified
                                // Proceed to the app's main activity
                                Toast.makeText(this, "SignIn Successful!", LENGTH_SHORT).show()
                                val intent = Intent(this, HomeActivity::class.java)
                                intent.putExtra("emailID", email)
                                startActivity(intent)
                            } else {
                                // User is authenticated but email is not verified
                                // You can show a message asking the user to verify their email
                                Toast.makeText(
                                    this,
                                    "Please verify your email first!",
                                    LENGTH_SHORT
                                ).show()

                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                baseContext,
                                "Authentication failed. Retry!",
                                LENGTH_SHORT
                            ).show()
                        }
                    }


            } else {

                if (!isEmailValid(email)) {
                    emailEditText.error = "Invalid email address"
                }
                if (!isPasswordValid(password)) {
                    passwordEditText.error =
                        "Password must contain at least 6 characters including a numeral"
                }
            }

        }
        val signUpButton: Button = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val forgotPasswordButton: Button = findViewById(R.id.forgotPasswordButton)
        forgotPasswordButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        val regex = "^(?=.*\\d)(?=.*[a-zA-Z]).{6,}\$"
        return password.matches(Regex(regex))
    }

    override fun onRestart() {
        super.onRestart()

        // Code to execute when the activity is restarting
        // This method is called when you return to the activity after it was stopped
        passwordEditText = findViewById(R.id.passwordEditText)
        passwordEditText.setText("")
    }
}
