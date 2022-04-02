package com.example.meiyong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PIN_Code : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        val random = (100000..999999).random()
        findViewById<TextView>(R.id.textView2).setText(random.toString())
    }
}