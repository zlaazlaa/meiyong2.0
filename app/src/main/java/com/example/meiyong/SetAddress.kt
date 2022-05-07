package com.example.meiyong

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.button.MaterialButton

class SetAddress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_address)

        initData(intent)

        findViewById<MaterialButton>(R.id.confirm_address).setOnClickListener {
            val intent = Intent()
            intent.putExtra("name", findViewById<EditText>(R.id.name).text.toString())
            intent.putExtra(
                "phone_number",
                findViewById<EditText>(R.id.phone_number_to_send).text.toString()
            )
            intent.putExtra("province", findViewById<EditText>(R.id.province).text.toString())
            intent.putExtra("city", findViewById<EditText>(R.id.city).text.toString())
            intent.putExtra("district", findViewById<EditText>(R.id.district).text.toString())
            intent.putExtra(
                "detail_address",
                findViewById<EditText>(R.id.detail_address).text.toString()
            )
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("name", findViewById<EditText>(R.id.name).text.toString())
        intent.putExtra(
            "phone_number",
            findViewById<EditText>(R.id.phone_number_to_send).text.toString()
        )
        intent.putExtra("province", findViewById<EditText>(R.id.province).text.toString())
        intent.putExtra("city", findViewById<EditText>(R.id.city).text.toString())
        intent.putExtra("district", findViewById<EditText>(R.id.district).text.toString())
        intent.putExtra(
            "detail_address",
            findViewById<EditText>(R.id.detail_address).text.toString()
        )
        setResult(RESULT_OK, intent)
        finish()
    }

    @SuppressLint("CutPasteId")
    private fun initData(intent: Intent) {
        val name = intent.getStringExtra("name")
        val phoneNumber = intent.getStringExtra("phone_number")
        val province = intent.getStringExtra("province")
        val city = intent.getStringExtra("city")
        val district = intent.getStringExtra("district")
        val detailAddress = intent.getStringExtra("detail_address")
        when (intent.getStringExtra("code")) {
            "1" -> {
                findViewById<TextView>(R.id.address_title).text = "寄件地址信息(从哪寄)"
                findViewById<EditText>(R.id.name).hint="寄件人姓名"
            }
            "2" -> {
                findViewById<TextView>(R.id.address_title).text = "收件地址信息(要寄到哪里)"
                findViewById<EditText>(R.id.name).hint="收件人姓名"
            }
        }
        if (name != "姓名")
            findViewById<TextView>(R.id.name).text = name
        if (phoneNumber != "手机号")
            findViewById<TextView>(R.id.phone_number_to_send).text = phoneNumber
        if (province != "")
            findViewById<TextView>(R.id.province).text = province
        if (city != "")
            findViewById<TextView>(R.id.city).text = city
        if (district != "")
            findViewById<TextView>(R.id.district).text = district
        if (detailAddress != "详细地址")
            findViewById<TextView>(R.id.detail_address).text = detailAddress
    }
}