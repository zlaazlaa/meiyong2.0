package com.example.meiyong

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.meiyong.MyApplication.Companion.context
import com.example.meiyong.ReturnDataClass.Express100Return.Express100Return
import com.example.meiyong.ReturnDataClass.MessageAPIReturn.MessageAPIReturn
import com.example.meiyong.ReturnDataClass.RegisterReturn.RegisterReturn
import com.google.gson.Gson
import http.OkHttp
import okhttp3.*
import okio.utf8Size
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit
import okhttp3.FormBody as FormBody1

class Register : AppCompatActivity() {
    private var registerCode = ""

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        ////
        val register = findViewById<View>(R.id.register2)
        register.setOnClickListener {
//            finish()
            val codeInput = findViewById<EditText>(R.id.code).text.toString()
            val password1 = findViewById<EditText>(R.id.password1).text.toString()
            val password2 = findViewById<EditText>(R.id.password2).text.toString()
            val phoneNumber = findViewById<EditText>(R.id.phone_number_edited).text.toString()
            val username = findViewById<EditText>(R.id.username).text.toString()
            when {
                codeInput != registerCode -> {
                    Toast.makeText(this, "验证码不正确", Toast.LENGTH_LONG).show()
                }
                password1 != password2 -> {
                    Toast.makeText(this, "两次密码不同", Toast.LENGTH_LONG).show()
                }
                else -> {
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
                            Log.e("OKHTTP", "$responseData")

                            val gson = Gson()
                            val responseBody = gson.fromJson(responseData, RegisterReturn::class.java)
                            Log.e("OKHTTP_REGISTER", responseBody.body)
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

        findViewById<Button>(R.id.button_get_code).setOnClickListener {
            val phone = findViewById<EditText>(R.id.phone_number_edited).text.toString()
            if (phone == "" || phone.length != 11) {
                Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_LONG).show()
            } else {
                registerCode = (100000..999999).random().toString()
//        findViewById<TextView>(R.id.textView2).setText(random.toString())
                val client: OkHttpClient = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .build()
                val body = okhttp3.FormBody.Builder()
                    .add("mobile", phone)
                    .add("param", "**code**:$registerCode,**minute**:5")
                    .add("smsSignId", "7c27b9337af4460286c6c36d974ef1f5")
                    .add("templateId", "a09602b817fd47e59e7c6e603d3f088d")
                    .build()
                val request: Request = Request
                    .Builder()
                    .header("Authorization", "APPCODE 6515cd7e7b7648de91332d24a8ce85a0")
                    .url("https://gyytz.market.alicloudapi.com/sms/smsSend")
                    .post(body)
                    .build()
                val call: Call = client.newCall(request)
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.e("OKHTTP  125", "ERRORvsdkujvhsdajikvhsdvhjksdv:${e.message}")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body?.string()
                        val gson = Gson()
                        val returnCode = gson.fromJson(responseData, MessageAPIReturn::class.java)
                        if (returnCode.code == "0") {
                            Looper.prepare()
                            Toast.makeText(context, "验证码发送成功", Toast.LENGTH_LONG).show()
                            Looper.loop()
                        }
                    }
                })
            }

        }
    }
}