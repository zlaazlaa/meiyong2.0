package com.example.meiyong

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


class SendExpress : AppCompatActivity() {
    private val item =
        arrayOf("9:00-11:00", "11:00-13:00", "13:00-15:00", "15:00-17:00", "17:00-19:00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_express)

        val txt = findViewById<TextView>(R.id.time_set_is)
        txt.text = item[0]
        val setTimeLinear = findViewById<LinearLayout>(R.id.set_time_linear)
        setTimeLinear.setOnClickListener {
            setTime()
        }

        findViewById<MaterialButton>(R.id.change_address).setOnClickListener {
            editAddress(1)
        }

        findViewById<MaterialButton>(R.id.change_address2).setOnClickListener {
            editAddress(2)
        }

        findViewById<LinearLayout>(R.id.start_address_data).setOnClickListener {
            editAddress(1)
        }

        findViewById<LinearLayout>(R.id.end_address_data).setOnClickListener {
            editAddress(2)
        }

        findViewById<MaterialButton>(R.id.cancel_button).setOnClickListener {
            finish()
        }

        findViewById<MaterialButton>(R.id.confirm_button).setOnClickListener {
            val receiveName = findViewById<TextView>(R.id.name_edited2).text
            val receivePhone = findViewById<TextView>(R.id.phone_number_edited2).text


            val jsonObjects = JSONObject()
            jsonObjects
                .put("userId", UserInformation.userId.toString())
                .put("expectedTime", findViewById<TextView>(R.id.time_set_is).text.toString())
                .put("deliveryAddress", 2)
                .put("receiverName", receiveName.toString())
                .put("phone", receivePhone.toString())
//                .put("deliveryAddress", 1)
                .put("packagePos", "25-5-4")
                .put("stationId", 2)
                .put("status",1)
            OkHttp.post("/Order", jsonObjects, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                    Log.e("OKHTTP_NEW_ORDER", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
                    Log.e("OKHTTP_NEW_ORDER", "${response.body?.string()}")
                }

            })

            finish()
        }

    }

    @SuppressLint("SetTextI18n", "CutPasteId")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> if (resultCode == RESULT_OK) {
                val name = data?.getStringExtra("name")
                val phoneNumber = data?.getStringExtra("phone_number")
                val province = data?.getStringExtra("province")
                val city = data?.getStringExtra("city")
                val district = data?.getStringExtra("district")
                val detailAddress = data?.getStringExtra("detail_address")
                if (province != "" || city != "" || district != "") {
                    findViewById<TextView>(R.id.province_edited).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.city_edited).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.district_edited).visibility = View.VISIBLE
                }
                if (name != "") findViewById<TextView>(R.id.name_edited).text = name
                if (phoneNumber != "") findViewById<TextView>(R.id.phone_number_edited).text = phoneNumber
                if (province != "") findViewById<TextView>(R.id.province_edited).text = province
                if (city != "") findViewById<TextView>(R.id.city_edited).text = city
                if (district != "") findViewById<TextView>(R.id.district_edited).text = district
                if (detailAddress != "") findViewById<TextView>(R.id.detail_address_edited).text = detailAddress
            }
            2 -> if (resultCode == RESULT_OK) {
                val name = data?.getStringExtra("name")
                val phoneNumber = data?.getStringExtra("phone_number")
                val province = data?.getStringExtra("province")
                val city = data?.getStringExtra("city")
                val district = data?.getStringExtra("district")
                val detailAddress = data?.getStringExtra("detail_address")
                if (province != "" || city != "" || district != "") {
                    findViewById<TextView>(R.id.province_edited2).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.city_edited2).visibility = View.VISIBLE
                    findViewById<TextView>(R.id.district_edited2).visibility = View.VISIBLE
                }
                if (name != "") findViewById<TextView>(R.id.name_edited2).text = name
                if (phoneNumber != "") findViewById<TextView>(R.id.phone_number_edited2).text = phoneNumber
                if (province != "") findViewById<TextView>(R.id.province_edited2).text = province
                if (city != "") findViewById<TextView>(R.id.city_edited2).text = city
                if (district != "") findViewById<TextView>(R.id.district_edited2).text = district
                if (detailAddress != "") findViewById<TextView>(R.id.detail_address_edited2).text = detailAddress
            }
        }
    }

    private fun editAddress(code: Int) {
        val intent = Intent(this, SetAddress::class.java)
        when (code) {
            1 -> {
                intent.putExtra("name", findViewById<TextView>(R.id.name_edited).text.toString())
                intent.putExtra(
                    "phone_number",
                    findViewById<TextView>(R.id.phone_number_edited).text.toString()
                )
                intent.putExtra(
                    "province",
                    findViewById<TextView>(R.id.province_edited).text.toString()
                )
                intent.putExtra("city", findViewById<TextView>(R.id.city_edited).text.toString())
                intent.putExtra(
                    "district",
                    findViewById<TextView>(R.id.district_edited).text.toString()
                )
                intent.putExtra(
                    "detail_address",
                    findViewById<TextView>(R.id.detail_address_edited).text.toString()
                )
            }
            2 -> {
                intent.putExtra("name", findViewById<TextView>(R.id.name_edited2).text.toString())
                intent.putExtra(
                    "phone_number",
                    findViewById<TextView>(R.id.phone_number_edited2).text.toString()
                )
                intent.putExtra(
                    "province",
                    findViewById<TextView>(R.id.province_edited2).text.toString()
                )
                intent.putExtra("city", findViewById<TextView>(R.id.city_edited2).text.toString())
                intent.putExtra(
                    "district",
                    findViewById<TextView>(R.id.district_edited2).text.toString()
                )
                intent.putExtra(
                    "detail_address",
                    findViewById<TextView>(R.id.detail_address_edited2).text.toString()
                )
            }
        }

        intent.putExtra("code", code.toString())
        startActivityForResult(intent, code)
    }

    private fun setTime() {
        val txt = findViewById<TextView>(R.id.time_set_is)
        txt.text = item[0]
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("请选择") //默认为0表示选中第一个项目
            .setSingleChoiceItems(
                item, 0
            ) { dialog, which ->
                txt.text = item[which]
//                    Toast.makeText(this@SendExpress, "你单选了" + item[which], Toast.LENGTH_LONG).show()
            }
            .setPositiveButton(
                "确认"
            ) { dialog, which ->
            }
            .setNegativeButton("取消", null)
            .create()
        alertDialog.show()
    }


}