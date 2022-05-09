package com.example.meiyong

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meiyong.MyApplication.Companion.context
import com.example.meiyong.ReturnDataClass.OrderListReturn.OrderListReturn
import com.example.meiyong.ReturnDataClass.OrderReturn.Body
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class ManageOrders : AppCompatActivity() {
    //    lateinit var orderList: OrderListReturn
    lateinit var orderList: ArrayList<Body>
    val handler: Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_orders)
        val orderType = intent.getStringExtra("order_type")
        if (orderType == "1") {
            findViewById<TextView>(R.id.order_type).text = "寄件订单列表"
        } else {
            findViewById<TextView>(R.id.order_type).text = "无人车派送订单列表"
        }
        OkHttp.get("/Order/1/100", object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP_ORDER", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val gson = Gson()
                val orderReturn = gson.fromJson(responseData, OrderListReturn::class.java)
                Log.e("OKHTTP_GSON", "$responseData")
                if (orderReturn.status == 200) {
                    orderList = orderReturn.body!!
                    handler.post {
                        val recyclerView = findViewById<RecyclerView>(R.id.orders_list)
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = OrderListAdapter()
                    }
                } else {
                    Looper.prepare();
                    Toast.makeText(context, "用户未登录", Toast.LENGTH_LONG).show()
                    finish()
                    Looper.loop()

                }
            }

        })


    }

    inner class OrderListAdapter : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val orderId: TextView = view.findViewById(R.id.order_id)
            val name: TextView = view.findViewById(R.id.name)
            val phone: TextView = view.findViewById(R.id.phone_number)
            val address: TextView = view.findViewById(R.id.address)
            val cuiOrder: MaterialButton = view.findViewById(R.id.cui_order)
            val cancelOrder: MaterialButton = view.findViewById(R.id.cancel_order)
            val orderStatus: TextView = view.findViewById(R.id.order_status)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.order_information_card, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.cancelOrder.setOnClickListener {
                val jsonObject = JSONObject()
                val id = viewHolder.orderId.text.toString().toLong()
                val status = viewHolder.orderStatus.text.toString()
                if (status != "3") {
                    Toast.makeText(context, "订单未在派送", Toast.LENGTH_LONG).show()
                } else {
                    jsonObject
                        .put("reason", "用户主动取消订单")
                        .put("orderId", id)
                    OkHttp.post("/Order/cancel", jsonObject, object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.e("OKHTTP_CANCEL_ORDER_ERROR", e.toString())
                        }

                        override fun onResponse(call: Call, response: Response) {
                            val responseData = response.body?.string()
                            Looper.prepare()
                            Toast.makeText(context, "$responseData", Toast.LENGTH_LONG).show()
                            Looper.loop()
                            Log.e("OKHTTP_CANCEL_ORDER", "$responseData")
                        }

                    })
                }
            }
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val order = orderList[position]
            holder.name.text = order.receiverName
            holder.orderId.text = order.orderId.toString()
            holder.phone.text = order.phone
            holder.address.text = order.deliverDeviceId.toString()
            holder.orderStatus.text = order.status.toString()
        }

        override fun getItemCount(): Int {
            return orderList.size
        }

    }


}