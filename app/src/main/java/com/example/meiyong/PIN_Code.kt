package com.example.meiyong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import com.example.meiyong.ReturnDataClass.PinReturn.PinReturn
import com.google.gson.Gson
import http.OkHttp.get
import http.OkHttp.sendOkHttpRequestGET
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class PIN_Code : AppCompatActivity() {
    private val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_code)
        val url2 = "http://192.168.0.139:80/code"
//        sendOkHttpRequestGET(url2, object : okhttp3.Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
////
//                val gson = Gson()
//                val pinClass = gson.fromJson(responseData, PinReturn::class.java)
//                val pinString = pinClass.body
//                val pin = pinString.subSequence(-4, -1)
//                findViewById<TextView>(R.id.textView2).setText(pin)
//                Log.e("OKHTTP_PIN_CODE", "$pin")
//            }
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("OKHTTP", "ERROR")
//            }
//        })
        get("/code", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(this@PIN_Code, "连接网络失败", Toast.LENGTH_SHORT).show()
                Log.e("OKHTTP_PIN_ERROR", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Log.e("OKHTTP_PIN", "$responseData")
                val gson = Gson()
                val pinClass = gson.fromJson(responseData, PinReturn::class.java)
                val pinString = pinClass.body
                val pin = pinString?.subSequence(7, 11)
                handler.post {
                    if(pinClass.status == 200) findViewById<TextView>(R.id.textView2).text = pin
                    else findViewById<TextView>(R.id.textView2).text = "用户未登录"
                }
            }

        })
//        val random = (100000..999999).random()
//        findViewById<TextView>(R.id.textView2).setText(random.toString())
    }
}