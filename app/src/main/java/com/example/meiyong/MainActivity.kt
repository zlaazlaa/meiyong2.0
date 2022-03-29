package com.example.meiyong

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.meiyong.databinding.ActivityMainBinding
import com.example.meiyong.ui.home.HomeFragment
import http.okhttp
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


//        okhttp.post("/User/loginIn")
//        val url2 = "http://123.56.232.18:8080/serverdemo/tag/toggleTagFollow"
//        okhttp.sendOkHttpRequestGET(url2, object : okhttp3.Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val responseData = response.body?.string()
//                Log.e("OKHTTP", "$responseData")
//            }
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//        })
    }
}