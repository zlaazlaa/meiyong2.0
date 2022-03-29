package http

import android.util.Log
import okhttp3.*
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import javax.xml.transform.OutputKeys


object okhttp {
    private val BASE_URL = "http://192.168.0.127:80"
    val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build()

    fun get(string: String) {
        Thread(Runnable {
            val request: Request = Request.Builder()
                .url(BASE_URL + string)
                .build()
            val call: Call = client.newCall(request)
            val response = call.execute()
            val body = response.body?.string()
            Log.e("OKHTTP", "get response: $body")
        }).start()
    }

    fun yiget(string: String) {
        val request: Request = Request.Builder()
            .url(BASE_URL + string)
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", "ERRORvsdkujvhsdajikvhsdvhjksdv:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                Log.e("OKHTTP", "get response: $body")
            }
        })
    }

    fun post(string: String) {
        val body = FormBody.Builder()
            .add("username","11")
            .add("password","2222")
            .build()
        val request = Request.Builder().url(BASE_URL + string)
            .post(body)
            .build()
        val call = client.newCall(request)
        Thread(Runnable {
            val response = call.execute()
            Log.e("OKHTTP", "${response.body?.string()}")
        }).start()
    }

    fun yipost(string: String) {
        val body = FormBody.Builder().build()
        val request = Request.Builder().url(BASE_URL + string)
            .post(body)
            .build()
        val call = client.newCall(request)
        val response = call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", "ERROR")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("OKHTTP", "${response.body?.string()}")
            }
        })
    }

    fun sendOkHttpRequestGET(address: String, callback: Callback) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(address)
            .build()
        client.newCall(request).enqueue(callback)
    }

}