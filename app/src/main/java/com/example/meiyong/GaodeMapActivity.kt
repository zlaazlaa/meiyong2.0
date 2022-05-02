package com.example.meiyong

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapWrapper
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.example.meiyong.R
import com.example.meiyong.webview.MAWebViewWrapper
import com.example.meiyong.webview.MyWebView
import http.OkHttp
import okhttp3.Call
import okhttp3.Request

/**
 * AMapV2地图中简单介绍一些Marker的用法.
 */
class GaodeMapActivity : AppCompatActivity(), AMap.OnMarkerClickListener {
    private var markerOption: MarkerOptions? = null
    private var aMap: AMap? = null
    private var webView: MyWebView? = null
    private var latlng = LatLng(39.761, 116.434)
    private var aMapWrapper: AMapWrapper? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gaode_map)
        /*
         * 设置离线地图存储目录，在下载离线地图或初始化地图设置; 使用过程中可自行设置, 若自行设置了离线地图存储的路径，
         * 则需要在离线地图下载和使用地图页面都进行路径设置
         */
        // Demo中为了其他界面可以使用下载的离线地图，使用默认位置存储，屏蔽了自定义设置
        // MapsInitializer.sdcardDir =OffLineMapUtils.getSdCacheDir(this);


//提前更新位置（前提是登录）
        val getGPS = GPSGetClass()
        getGPS.UpdateCoordinate()


        webView = findViewById<View>(R.id.gao_de_map) as MyWebView
        val webViewWrapper = MAWebViewWrapper(webView)
        aMapWrapper = AMapWrapper(this, webViewWrapper)


//        webView!!.setOnTouchListener(OnTouchListener { view, motionEvent ->
//            aMapWrapper!!.onTouchEvent(
//                motionEvent
//            )
//        })
        aMapWrapper!!.onCreate() // 此方法必须重写
        aMapWrapper!!.getMapAsyn { map ->
            aMap = map
            aMap!!.setOnMapLongClickListener { point ->
                Log.e(
                    "mapcore",
                    "onMapLongClick $point"
                )
            }
            addMarkersToMap() // 往地图上添加marker
        }
    }

    /**
     * 初始化AMap对象
     */
    private fun init() {

    }

    /**
     * 方法必须重写
     */
    override fun onResume() {
        super.onResume()
        aMapWrapper!!.onResume()
    }

    /**
     * 方法必须重写
     */
    override fun onPause() {
        super.onPause()
        aMapWrapper!!.onPause()
    }

    /**
     * 方法必须重写
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        aMapWrapper!!.onSaveInstanceState(outState)
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        aMapWrapper!!.onDestroy()
    }

    var marker: Marker? = null
    var marker1: Marker? = null

    /**
     * 在地图上添加marker
     */
    private fun addMarkersToMap() {



        val GPS_84 = GPSGetClass.GPSCoordinate
        val GPSCoordiante = GPSGetClass.GPSUtil.gps84_To_Gcj02(GPS_84.Latitude, GPS_84.Longitude)
        latlng = LatLng(GPSCoordiante[0], GPSCoordiante[1])


        markerOption = MarkerOptions()
            .position(latlng)
            .draggable(false).title("无人车位置")
            .setInfoWindowOffset(-10, -10)
        marker1 = aMap!!.addMarker(markerOption)
        marker1?.zIndex = 2000f
        marker1?.showInfoWindow()
        aMap!!.animateCamera(CameraUpdateFactory.newLatLng(latlng))
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(17F))
//        aMap!!.addMarker(
//            MarkerOptions().position(LatLng(latlng.latitude, latlng.longitude + 0.001))
//                .title("test")
//                .rotateAngle(90f)
//        )
//        aMap!!.addMarker(
//            MarkerOptions().position(LatLng(latlng.latitude, latlng.longitude + 0.002))
//                .title("test")
//                .rotateAngle(90f)
//        )
//        aMap!!.addMarker(
//            MarkerOptions().position(LatLng(latlng.latitude, latlng.longitude + 0.003))
//                .title("test")
//        )
//        marker = aMap!!.addMarker(
//            MarkerOptions().position(LatLng(latlng.latitude, latlng.longitude + 0.004))
//                .title("test")
//        )
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.e("mapcore", "onMarkerClick markeractivity  " + marker.id)
        return false
    }

    private fun getBitmapDescriptorOfOther(resID: Int): BitmapDescriptor? {
        var descriptor: BitmapDescriptor? = null
        val view = TextView(this)
        view.setTypeface(Typeface.DEFAULT, Typeface.BOLD)
        view.gravity = Gravity.CENTER
        view.setBackgroundResource(resID)
        descriptor = BitmapDescriptorFactory.fromView(view)
        return descriptor
    }

    fun dip2px(dpValue: Float): Int {
        val scale = resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }


    var markers: MutableList<Marker> = ArrayList()
    var bitmapDescriptors: MutableList<BitmapDescriptor?> = ArrayList()
    private fun testRemoveMarker() {
        for (marker in markers) {
            marker.remove()
        }
        markers.clear()
    }

}