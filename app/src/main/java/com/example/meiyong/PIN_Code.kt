package com.example.meiyong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import http.OkHttp.get
import http.OkHttp.sendOkHttpRequestGET
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class PIN_Code : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        val url2 = "http://192.168.0.122:80/code"
        sendOkHttpRequestGET(url2, object : okhttp3.Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Log.e("OKHTTP CODE", "$responseData")
            }
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", "ERROR")
            }
        })
        get("/code")
        val random = (100000..999999).random()
        findViewById<TextView>(R.id.textView2).setText(random.toString())
    }
}