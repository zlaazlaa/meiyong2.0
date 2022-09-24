package com.example.meiyong

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.meiyong.MyApplication.Companion.context
import com.example.meiyong.ReturnDataClass.OrderCancelReturn.OrderCancelReturn
import com.example.meiyong.ReturnDataClass.OrderCreateReturn.OrderCreateReturn
import com.example.meiyong.ReturnDataClass.OrderListReturn.OrderListReturn
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import org.w3c.dom.Text
import java.io.IOException

class NewNobodyCarOrder : AppCompatActivity() {
    private val item_address =
        arrayOf("梅苑", "兰苑", "竹苑", "菊苑", "桃苑", "李苑", "柳苑", "桂苑", "荷苑", "樱苑", "青教")

    private val item_time =
        arrayOf("9:00-11:00", "11:00-13:00", "13:00-15:00", "15:00-17:00", "17:00-19:00")

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_nobody_car_order)
        findViewById<TextView>(R.id.detail_address_textview).text = item_address[0]
        findViewById<LinearLayout>(R.id.set_time_linear).setOnClickListener {
            setTime()
        }
        findViewById<LinearLayout>(R.id.detail_address).setOnClickListener {
            alertDialogListen()
        }
        findViewById<ImageButton>(R.id.set_adress).setOnClickListener {
            alertDialogListen()
        }
        findViewById<MaterialButton>(R.id.confirm_address).setOnClickListener {
            val receiveName = findViewById<TextView>(R.id.name).text
            val receivePhone = findViewById<TextView>(R.id.phone_number_to_send).text
            val packagePos =
                findViewById<TextView>(R.id.Pos1).text.toString() + "-" + findViewById<TextView>(R.id.Pos2).text.toString() + "-" + findViewById<TextView>(
                    R.id.Pos3
                ).text.toString()
            val jsonObjects = JSONObject()
            jsonObjects
                .put("userId", UserInformation.userId)
                .put("expectedTime", findViewById<TextView>(R.id.time_set_is).text.toString())
                .put("deliveryAddress", 2)
                .put("receiverName", receiveName.toString())
                .put("phone", receivePhone.toString().toLong())
//                .put("deliveryAddress", 1)
                .put("packagePos", packagePos)
                .put("stationId", 2)
                .put("status", 1)
            OkHttp.post("/Order", jsonObjects, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(context, "网络连接错误，请检查本地网络连接", Toast.LENGTH_LONG).show()
                    Log.e("OKHTTP_NEW_ORDER", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    Looper.prepare()
                    val responseData = response.body?.string()
                    val gson = Gson()
                    if (responseData != null) {
                        Log.e("OKHTTP_NEW_ORDER", responseData)
                        val returnData = gson.fromJson(responseData, OrderCreateReturn::class.java)
                        if (returnData.status == 500) {
                            Toast.makeText(context, "驿站正在休息，请稍后再试", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "订单创建成功", Toast.LENGTH_LONG).show()
                            finish()
                        }
                    }
                    Looper.loop()
                }
            })
        }
    }

    fun alertDialogListen() {
        val txt = findViewById<TextView>(R.id.detail_address_textview)
        var nowNumber = 0
        for ((i, e) in item_address.withIndex())
            if (e == txt.text) {
                nowNumber = i
                break
            }
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("请选择") //默认为0表示选中第一个项目
            .setSingleChoiceItems(
                item_address, nowNumber
            ) { dialog, which ->
                txt.text = item_address[which]
//                    Toast.makeText(this@SendExpress, "你单选了" + item[which], Toast.LENGTH_LONG).show()
            }
            .setPositiveButton(
                "确认"
            ) { dialog, which ->
//                    txt.setText(item[which])
            }
            .setNegativeButton("取消") { dialog, which ->
                txt.text = item_address[nowNumber]
            }
            .create()
        alertDialog.show()
    }

    private fun setTime() {
        val txt = findViewById<TextView>(R.id.time_set_is)
        var nowNumber = 0
        for ((i, e) in item_time.withIndex())
            if (e == txt.text) {
                nowNumber = i
                break
            }
        val alertDialog = android.app.AlertDialog.Builder(this)
            .setTitle("请选择") //默认为0表示选中第一个项目
            .setSingleChoiceItems(
                item_time, nowNumber
            ) { dialog, which ->
                txt.text = item_time[which]
//                    Toast.makeText(this@SendExpress, "你单选了" + item[which], Toast.LENGTH_LONG).show()
            }
            .setPositiveButton(
                "确认"
            ) { dialog, which ->
            }
            .setNegativeButton("取消") { dialog, which ->
                txt.text = item_time[nowNumber]
            }
            .create()
        alertDialog.show()
    }
}