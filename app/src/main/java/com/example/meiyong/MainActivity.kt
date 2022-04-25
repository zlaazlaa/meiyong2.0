package com.example.meiyong

import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.meiyong.databinding.ActivityMainBinding
import http.OkHttp
import http.OkHttp.sendOkHttpRequestGET
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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


        
//        OkHttp.post("/User/loginIn")
//
//
//
//
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