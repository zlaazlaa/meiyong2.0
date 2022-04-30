package com.example.meiyong

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.meiyong.ReturnDataClass.RegisterReturn.RegisterReturn
import com.google.gson.Gson
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        ////
        val register = findViewById<View>(R.id.register2)
        register.setOnClickListener {
//            finish()
            val password1 = findViewById<EditText>(R.id.password1).text.toString()
            val password2 = findViewById<EditText>(R.id.password2).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.phone_number_edited).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            if (password1 != password2) {
                Toast.makeText(this, "两次密码不同", Toast.LENGTH_LONG).show()
            } else {
                val jsonData = JSONObject()
                jsonData
                    .put("username", username)
                    .put("password", password1)
                    .put("phoneNumber", phoneNumber)
                OkHttp.post("/User/register", jsonData, object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
//                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()
                        Log.e("OKHTTP","$responseData")

                        val gson = Gson()
                        val responseBody = gson.fromJson(responseData, RegisterReturn::class.java)
                        Log.e("OKHTTP_REGISTER", "${responseBody.body}")
//                        Toast.makeText(
//                            MyApplication.context,
//                            "${responseBody.body}",
//                            Toast.LENGTH_LONG
//                        ).show()
                        finish()
                    }
                })
            }
        }
    }
}