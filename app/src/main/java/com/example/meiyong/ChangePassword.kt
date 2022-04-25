package com.example.meiyong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.internal.http.hasBody
import org.json.JSONObject
import java.io.IOException

class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            Toast.makeText(this, "正在申请更改密码", Toast.LENGTH_LONG).show()
            val old = findViewById<EditText>(R.id.old_password).text
            val new = findViewById<EditText>(R.id.new_password).text
            val jsonObject = JSONObject()
            jsonObject
                .put("oldPassword", old)
                .put("newPassword", new)
            OkHttp.put("/User/password", jsonObject, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    Log.e("OKHTTP_CHANGE_PASSWORD", "$responseData")
//                    Log.e("OKHTTP_CHANGE_PASSWORD", response.toString())
//                    TODO("Not yet implemented")
                }
            })
        }
    }
}