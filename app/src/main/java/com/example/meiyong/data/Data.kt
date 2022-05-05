package com.example.meiyong.data

import android.util.Log
import com.example.meiyong.ReceiveClass.StudyjsonData
import com.example.meiyong.ReceiveClass.StudyjsonExpressData
import com.google.gson.Gson
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class Data {
    companion object {
        var expressList = ArrayList<StudyjsonExpressData>()
    }

    val StudyAPIurl =
        "http://123.56.232.18:8080/serverdemo/tag/queryTagList?offset=1231321&pageCount=10&tagId=3&tagType=all&userId=33"

    fun updateExpressData() {
        expressList.clear()
        OkHttp.sendOkHttpRequestGET(StudyAPIurl, object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                val responseData = response.body?.string()
//                val typeOf = object : TypeToken<ArrayList<queryOrder>>() {}.type
                Log.e("OKHTTP", "$responseData")
                val express_list = gson.fromJson(responseData, StudyjsonData::class.java)
                expressList.clear()
                for ((cnt, ex) in express_list.data.data.withIndex()) {
                    expressList.add(cnt, ex)
                }

//                val express_list = gson.fromJson(responseData, queryOrder::class.java)
//                for ((cnt, ex) in express_list) {
//                    expressList.add(cnt, ex)
//                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", "ERROR")
            }
        })
    }
}