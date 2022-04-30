package com.example.meiyong

import android.util.Log
import com.google.gson.Gson
import http.GetGPSReturnData.Coordinate
import http.GetGPSReturnData.GetGPSReturn
import http.LoginReturnData.LoginReturn
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.Response
import java.io.IOException


class GPSGetClass {
    companion object {
        lateinit var GPSLoginInformation: LoginReturn
        var GPSCoordinate = Coordinate(100.0,100.0)
        lateinit var GPSGetReturn: GetGPSReturn
    }

    fun LoginGPS() {
        val LoginFormBody = FormBody.Builder()
            .add("lang", "zh_CN")
            .add("imei", "65302631125")
            .add("password", "123456")
            .build()
        http.GPSOkHttp.post("/locator-app/imeiLoginVerification", LoginFormBody, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP_GPS_LOGIN", "Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val gson = Gson()
                GPSLoginInformation = gson.fromJson(responseData, LoginReturn::class.java)
                Log.e("OKHTTP_GPS_LOGIN", "$responseData")
            }

        })
    }

    fun UpdateCoordinate() {
        val GetGPSFormBody = FormBody.Builder()
            .add("imei", "65302631125")
            .add("coorType", "bd09ll")//BaiduMap
//            .add("coorType", "GCJ02")//GoogleMap
            .add("token", GPSLoginInformation.data.token)
            .build()
        http.GPSOkHttp.post("/locator-app/redis/getGps", GetGPSFormBody, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP_GPS_GET_GPS", "Fail")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Log.e("OKHTTP_GPS_GET_GPS", "$responseData")
                val gson = Gson()
                GPSGetReturn = gson.fromJson(responseData, GetGPSReturn::class.java)
                GPSCoordinate.Latitude = GPSGetReturn.data.latitude
                GPSCoordinate.Longitude = GPSGetReturn.data.longitude
            }

        })
    }

    fun getCoordiante(): Coordinate {
        return GPSCoordinate
    }


    /**
     * 各地图API坐标系统比较与转换;
     * WGS84坐标系：即地球坐标系，国际上通用的坐标系。设备一般包含GPS芯片或者北斗芯片获取的经纬度为WGS84地理坐标系,
     * 谷歌地图采用的是WGS84地理坐标系（中国范围除外）;
     * GCJ02坐标系：即火星坐标系，是由中国国家测绘局制订的地理信息系统的坐标系统。由WGS84坐标系经加密后的坐标系。
     * 谷歌中国地图和搜搜中国地图采用的是GCJ02地理坐标系; BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系;
     * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
     *
     *
     * WGS-84 地心坐标系，即GPS原始坐标体系
     * GCJ-02火星坐标系，国测局02年发布的坐标体系，它是一种对经纬度数据的加密算法，即加入随机的偏差。高德、腾讯、Google中国地图使用。国内最广泛使用的坐标体系；
     * 一般都是由GCJ-02进过偏移算法得到的。这种体系就根据每个公司的不同，坐标体系都不一样了。
     * 比如，百度的BD-09坐标、搜狗坐标等
     */
    object GPSUtil {
        var pi = 3.1415926535897932384626
        var x_pi = 3.14159265358979324 * 3000.0 / 180.0
        var a = 6378245.0
        var ee = 0.00669342162296594323
        fun transformLat(x: Double, y: Double): Double {
            var ret =
                -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x))
            ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0
            ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0
            ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0
            return ret
        }

        fun transformLon(x: Double, y: Double): Double {
            var ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + (0.1
                    * Math.sqrt(Math.abs(x)))
            ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0
            ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0
            ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(
                x / 30.0
                        * pi
            )) * 2.0 / 3.0
            return ret
        }

        fun outOfChina(lat: Double, lon: Double): Boolean {
            if (lon < 72.004 || lon > 137.8347) return true
            return if (lat < 0.8293 || lat > 55.8271) true else false
        }

        /**
         * 84 to 火星坐标系 (GCJ-02) World Geodetic System ==> Mars Geodetic System
         *
         * @param lat
         * @param lon
         * @return
         */
        fun gps84_To_Gcj02(lat: Double, lon: Double): DoubleArray {
            if (outOfChina(lat, lon)) {
                return doubleArrayOf(lat, lon)
            }
            var dLat = transformLat(lon - 105.0, lat - 35.0)
            var dLon = transformLon(lon - 105.0, lat - 35.0)
            val radLat = lat / 180.0 * pi
            var magic = Math.sin(radLat)
            magic = 1 - ee * magic * magic
            val sqrtMagic = Math.sqrt(magic)
            dLat = dLat * 180.0 / (a * (1 - ee) / (magic * sqrtMagic) * pi)
            dLon = dLon * 180.0 / (a / sqrtMagic * Math.cos(radLat) * pi)
            val mgLat = lat + dLat
            val mgLon = lon + dLon
            return doubleArrayOf(mgLat, mgLon)
        }

        /**
         * * 火星坐标系 (GCJ-02) to 84 * * @param lon * @param lat * @return
         */
        fun gcj02_To_Gps84(lat: Double, lon: Double): DoubleArray {
            val gps = gps84_To_Gcj02(lat, lon)
            val lontitude = lon * 2 - gps[1]
            val latitude = lat * 2 - gps[0]
            return doubleArrayOf(latitude, lontitude)
        }

        /**
         * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 将 GCJ-02 坐标转换成 BD-09 坐标
         *
         * @param lat
         * @param lon
         */
        fun gcj02_To_Bd09(lat: Double, lon: Double): DoubleArray {
            val z =
                Math.sqrt(lon * lon + lat * lat) + 0.00002 * Math.sin(lat * x_pi)
            val theta =
                Math.atan2(lat, lon) + 0.000003 * Math.cos(lon * x_pi)
            val tempLon = z * Math.cos(theta) + 0.0065
            val tempLat = z * Math.sin(theta) + 0.006
            return doubleArrayOf(tempLat, tempLon)
        }

        /**
         * * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换算法 * * 将 BD-09 坐标转换成GCJ-02 坐标 * * @param
         * bd_lat * @param bd_lon * @return
         */
        fun bd09_To_Gcj02(lat: Double, lon: Double): DoubleArray {
            val x = lon - 0.0065
            val y = lat - 0.006
            val z =
                Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi)
            val theta =
                Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi)
            val tempLon = z * Math.cos(theta)
            val tempLat = z * Math.sin(theta)
            return doubleArrayOf(tempLat, tempLon)
        }

        /**将gps84转为bd09
         * @param lat
         * @param lon
         * @return
         */
        fun gps84_To_bd09(lat: Double, lon: Double): DoubleArray {
            val gcj02 = gps84_To_Gcj02(lat, lon)
            return gcj02_To_Bd09(gcj02[0], gcj02[1])
        }

        fun bd09_To_gps84(lat: Double, lon: Double): DoubleArray {
            val gcj02 = bd09_To_Gcj02(lat, lon)
            val gps84 = gcj02_To_Gps84(gcj02[0], gcj02[1])
            //保留小数点后六位
            gps84[0] = retain6(gps84[0])
            gps84[1] = retain6(gps84[1])
            return gps84
        }

        /**保留小数点后六位
         * @param num
         * @return
         */
        private fun retain6(num: Double): Double {
            val result = String.format("%.6f", num)
            return java.lang.Double.valueOf(result)
        }
    }
}