package http.LoginReturnData

class Body(
    val deviceType: Int,
    val simCardNumber: String,
    val chargeModel: String,
    val expireTime: Int,
    val deviceIcon: String,
    val phoneNum: String,
    val deviceModel: String,
    val deviceName: String,
    val token: String
)