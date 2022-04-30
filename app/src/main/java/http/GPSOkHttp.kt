package http

import android.util.Log
import http.LoginReturnData.LoginReturn
import okhttp3.*
import java.util.concurrent.TimeUnit


object GPSOkHttp {
    private val BASE_URL = "http://app.tuchuang123.com:80"

    private var url1: String = ""
    private val cookieStore: HashMap<String, List<Cookie>> = HashMap()
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .cookieJar(object : CookieJar {
            override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
                cookieStore[url.host] = cookies
                if (url1 == "") url1 =
                    cookies.toString().substring(1, cookies.toString().indexOf(';'))
                Log.e("OKHTTP  123", cookieStore[url.host].toString())
            }

            override fun loadForRequest(url: HttpUrl): List<Cookie> {
                val cookies = cookieStore[url.host]
                Log.e("OKHTTPCOOKIE", cookies.toString())
                return cookies ?: ArrayList()
            }
        })
        .build()


    fun post(string: String, formBody: FormBody, callback: Callback) {
        val request: Request = Request
            .Builder()
            .url(BASE_URL + string)
            .post(formBody)
            .build()
        client.newCall(request).enqueue(callback)
    }


}