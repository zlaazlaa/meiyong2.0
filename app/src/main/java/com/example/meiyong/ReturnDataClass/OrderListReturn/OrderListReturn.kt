package com.example.meiyong.ReturnDataClass.OrderListReturn

import com.example.meiyong.ReturnDataClass.OrderReturn.Body

class OrderListReturn(
    val status: Int,
    val err: String,
    val body: ArrayList<Body>?
)