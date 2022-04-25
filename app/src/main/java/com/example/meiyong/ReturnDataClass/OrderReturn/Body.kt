package com.example.meiyong.ReturnDataClass.OrderReturn

class Body(
    val orderId: Byte?,
    val receiverName: String,
    val phone: String,
    val deliveryAddress: Byte?,
    val packagePos: Byte?,
    val startTime : Time,
    val deliverStartTime: Time,
    val stationId: Int,
    val endTime: Time,
    val deliverDeviceId: Int,
    val status: Byte?
)