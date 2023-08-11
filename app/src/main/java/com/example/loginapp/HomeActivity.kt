package com.example.loginapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.loginapp.databinding.ActivityHomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var tvHomeGreet: TextView
    private lateinit var btnHomeLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val receivedData=intent.getStringExtra("emailID").toString()
        tvHomeGreet=findViewById(R.id.tvHomeGreet)
        btnHomeLogout=findViewById(R.id.btnHomeLogout)
        tvHomeGreet.text = receivedData
        btnHomeLogout.setOnClickListener {

            Firebase.auth.signOut()
            /*val intent1= Intent(this,MainActivity::class.java)
            startActivity(intent1)*/
            finish()
        }
    }
    override fun onStop() {
        super.onStop()

        // Your code to handle what happens when the activity enters the onStop state
        // For example, you might release resources or pause ongoing tasks.
        finish()
    }
}


