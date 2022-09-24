package com.example.meiyong.data

import android.util.Log
import android.widget.Toast
import com.example.meiyong.MyApplication.Companion.context
import com.example.meiyong.ReturnDataClass.LoginReturn.LoginReturn
import com.example.meiyong.ReturnDataClass.UserInformationReturn.UserInformationReturn
import com.example.meiyong.UserInformation
import com.example.meiyong.data.model.LoggedInUser
import com.google.gson.Gson
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    fun login(username: String, password: String): Result<LoggedInUser> {
        var tag = 0
        try {
            val jsonObject = JSONObject()
            jsonObject
                .put("username", username)
                .put("password", password)
            OkHttp.post("/User/loginIn", jsonObject, object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
                    Log.e("OKHTTP_FAIL", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    val gson = Gson()
                    val loginReturn: LoginReturn =
                        gson.fromJson(responseData, LoginReturn::class.java)
                    if (loginReturn.status == 200) {
                        tag = 1
//                        TODO("添加userID")
                        UserInformation.userId = loginReturn.body.userId
                    } else {
                        tag = 0
                    }
                }
            })
            return if (tag == 1) {
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)


                OkHttp.get("/User/now", object : okhttp3.Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Toast.makeText(context, "网络连接失败", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseData = response.body.toString()
                        Log.e("OKHTTP_USER_NOW", responseData)
                        val gson = Gson()
                        val information = gson.fromJson(responseData, UserInformationReturn::class.java)
                        UserInformation.userId = information.body.userId
                        UserInformation.nickname = information.body.nickname
                        UserInformation.phoneNumber = information.body.phoneNumber
                        UserInformation.role = information.body.role
                        UserInformation.username = information.body.username
                        val asfsa = 1
                    }

                })
                Result.Success(user)
            } else {
                val user = LoggedInUser(java.util.UUID.randomUUID().toString(), username)
                Result.Success(user)
//                return Result.Error(IOException("Error logging in", e))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}