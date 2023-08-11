package com.example.loginapp

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var auth: FirebaseAuth
    private lateinit var resetPasswordButton: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        emailEditText = findViewById(R.id.emailEditText)
        auth= Firebase.auth
        progressBar=findViewById(R.id.progressBar)

        resetPasswordButton = findViewById(R.id.resetPasswordButton)
        resetPasswordButton.setOnClickListener {
            // Handle forgot password logic here
            progressBar.visibility= View.VISIBLE
            val email = emailEditText.text.toString()
            if (isEmailValid(email)) {
                // Send password reset email
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        progressBar.visibility= View.GONE
                        if (task.isSuccessful) {
                            // Password reset email sent successfully
                            // You can show a success message to the user
                            Toast.makeText(this,"Check your email and then go to login",LENGTH_SHORT).show()
                        } else {
                            // Password reset email failed
                            // You can show an error message to the user
                            Toast.makeText(this,"Process failed. Try again!",LENGTH_SHORT).show()
                        }
                    }



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
