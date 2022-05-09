package com.example.meiyong.ReturnDataClass.OrderReturn

class Body(
    val orderId: Int?,
    val receiverName: String,
    val phone: String,
    val deliveryAddress: Int,
    val packagePos: String,
    val startTime : Time?,
    val deliverStartTime: Time?,
    val stationId: Int,
    val endTime: Time?,
    val deliverDeviceId: Int?,
    val status: Byte?
)