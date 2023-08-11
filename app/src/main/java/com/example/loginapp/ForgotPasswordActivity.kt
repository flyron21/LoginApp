package com.example.loginapp

import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailEditText = findViewById(R.id.emailEditText)

        val resetPasswordButton: Button = findViewById(R.id.resetPasswordButton)
        resetPasswordButton.setOnClickListener {
            // Handle forgot password logic here
            val email = emailEditText.text.toString()
            if (isEmailValid(email)) {
                // Send password reset email

            }
            else {
                // Show error messages for invalid email or password
                emailEditText.error = "Invalid email address"
            }
        }
    }
    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    override fun onStop() {
        super.onStop()

        // Your code to handle what happens when the activity enters the onStop state
        // For example, you might release resources or pause ongoing tasks.
        finish()
    }
}
