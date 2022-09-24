package com.example.meiyong.data

import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meiyong.MyApplication
import com.example.meiyong.R
import com.example.meiyong.ReceiveClass.StudyjsonExpressData
import com.example.meiyong.ReturnDataClass.OrderListReturn.OrderListReturn
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
        OkHttp.get("/Order/1/100", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP_ORDER", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                val gson = Gson()
//                val orderReturn = gson.fromJson(responseData, OrderListReturn::class.java)
//                Log.e("OKHTTP_GSON", "$responseData")
//                if (orderReturn.status == 200) {
//                    expressList = orderReturn.body!!
//                        val recyclerView = findViewById<RecyclerView>(R.id.orders_list)
//                        recyclerView.layoutManager = LinearLayoutManager(MyApplication.context)
//                        recyclerView.adapter = OrderListAdapter()
//                } else {
//                    Looper.prepare();
//                    Toast.makeText(MyApplication.context, "用户未登录", Toast.LENGTH_LONG).show()
//                    finish()
//                    Looper.loop()
//
//                }
            }

        })
    }


//    fun updateExpressData() {
//        expressList.clear()
//        OkHttp.sendOkHttpRequestGET(StudyAPIurl, object : Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val gson = Gson()
//                val responseData = response.body?.string()
////                val typeOf = object : TypeToken<ArrayList<queryOrder>>() {}.type
//                Log.e("OKHTTP", "$responseData")
//                val express_list = gson.fromJson(responseData, OrderListReturn::class.java)
//                expressList.clear()
//                for ((cnt, ex) in express_list.data.data.withIndex()) {
//                    expressList.add(cnt, ex)
//                }
//
////                val express_list = gson.fromJson(responseData, queryOrder::class.java)
////                for ((cnt, ex) in express_list) {
////                    expressList.add(cnt, ex)
////                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("OKHTTP", "ERROR")
//            }
//        })
//    }
}