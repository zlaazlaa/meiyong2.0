package com.example.meiyong

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.amap.api.maps.AMapWrapper
import com.example.meiyong.data.Data
import com.example.meiyong.databinding.ActivityMainBinding
import com.example.meiyong.webview.MAWebViewWrapper
import com.example.meiyong.webview.MyWebView
import com.google.android.material.bottomnavigation.BottomNavigationView
import http.OkHttp
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var webView: MyWebView? = null
        var aMapWrapper: AMapWrapper? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Data().updateExpressData()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val GPSSSS = GPSGetClass()
//        GPSSSS.LoginGPS()

//        GetAMapWrapper().getAMapWrapper(this)







//        setContentView(R.layout.activity_gaode_map)
//        webView = findViewById<View>(R.id.gao_de_map) as MyWebView
//        val webViewWrapper = MAWebViewWrapper(webView)
//        aMapWrapper = AMapWrapper(this, webViewWrapper)


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED
//            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//            != PackageManager.PERMISSION_GRANTED
//            || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            != PackageManager.PERMISSION_GRANTED
//        ) {
////            Toast.makeText(applicationContext, "没有权限,请手动开启定位权限", Toast.LENGTH_SHORT).show()
//        // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
//            ActivityCompat.requestPermissions(
//                this@MainActivity,
//                arrayOf(
//                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ),
//                100
//            )
//        }

//        OkHttp.post("/User/loginIn")

//        OkHttp.get("/Order/1")


//        findViewById<Button>(R.id.button_login).setOnClickListener {
//            OkHttp.post("/User/loginIn")
////
//        }
//        findViewById<Button>(R.id.button_get).setOnClickListener {
//            OkHttp.get("/Order/1")
//        }


//        val url2 = "http://192.168.0.127:80/Order/1"
//        sendOkHttpRequestGET(url2, object : okhttp3.Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                Log.e("OKHTTP", "$responseData")
//            }
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("OKHTTP", "ERROR")
//            }
//        })
    }

}