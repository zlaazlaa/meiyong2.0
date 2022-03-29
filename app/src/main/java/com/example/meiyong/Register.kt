package com.example.meiyong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        ////
        val register = findViewById<View>(R.id.register2)
        register.setOnClickListener {
            finish()
        }
    }
}