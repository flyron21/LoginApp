package com.example.loginapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    private lateinit var tvHomeGreet: TextView
    private lateinit var btnHomeLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val receivedData = intent.getStringExtra("emailID").toString()
        tvHomeGreet = findViewById(R.id.tvHomeGreet)
        btnHomeLogout = findViewById(R.id.btnHomeLogout)
        tvHomeGreet.text = receivedData
        btnHomeLogout.setOnClickListener {

            Firebase.auth.signOut()
            /*val intent1= Intent(this,MainActivity::class.java)
            startActivity(intent1)*/
            finish()
        }
    }

}


