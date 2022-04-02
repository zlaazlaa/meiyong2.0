package http

import android.util.Log
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit


object OkHttp {
    private const val BASE_URL = "http://192.168.0.127:80"
    private var url1: String = ""
    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .cookieJar(object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
                Log.e("OKHTTP", cookieStore[url.host].toString())
                url1 = cookies.toString().substring(cookies.toString().indexOf('=') + 1,cookies.toString().indexOf(';'))
            }


            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host]
                return cookies ?: ArrayList()
            }
        })
        .build()
    fun get(string: String) {
        Log.e("OKHTTP",cookieStore["192.168.0.127"].toString())
        Thread(Runnable {
            val request: Request = Request.Builder()
                .url(BASE_URL + string)
                .addHeader("Cookie", cookieStore["192.168.0.127"].toString())
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


    private val jsonType get() = "application/json; charset=utf-8".toMediaTypeOrNull()
    fun post(string: String) {
        val jsonObject = JSONObject()
        jsonObject
            .put("username", "11")
            .put("password", "2222")


        val body: RequestBody = jsonObject.toString().toRequestBody(jsonType)

        val request: Request = Request
            .Builder()
            .url(BASE_URL + string)
            .post(body)
            .build()
        val call = client.newCall(request)
        Thread(Runnable {
            val response = call.execute()
            Log.e("OKHTTP", "${response.body?.string()}")
        }).start()
    }

//    private val cookieJar: CookieJar = object : CookieJar {
//        private val map = HashMap<String, MutableList<Cookie>>()
//        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
//            map[url.host] = cookies as MutableList<Cookie>
//        }
//
//        override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
//            return map[url.host] ?: ArrayList()
//        }
//    }


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

