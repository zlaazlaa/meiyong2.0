package com.example.meiyong

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.amap.api.maps.AMapWrapper
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.example.meiyong.MyApplication.Companion.context
import com.example.meiyong.ReturnDataClass.Express100Return.Express100Return
import com.example.meiyong.ReturnDataClass.Express100Return.List
import com.example.meiyong.webview.MAWebViewWrapper
import com.example.meiyong.webview.MyWebView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.concurrent.timer

/**
 * AMapV2地图中简单介绍一些Marker的用法.
 */
class GaodeMapActivity : AppCompatActivity(), AMap.OnMarkerClickListener {
    private var markerOption: MarkerOptions? = null
    private var aMap: AMap? = null
    private var webView: MyWebView? = null
    private var latlng = LatLng(39.761, 116.434)
    private var aMapWrapper: AMapWrapper? = null
    lateinit var expressInformationList: ArrayList<List>
    val handler: Handler = Handler()

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


//        val aMapWrapper = GetAMapWrapper().returnAMapWrapper(this)


//        webView!!.setOnTouchListener(OnTouchListener { view, motionEvent ->
//            aMapWrapper!!.onTouchEvent(
//                motionEvent
//            )
//        })
        aMapWrapper?.onCreate() // 此方法必须重写
        aMapWrapper?.getMapAsyn { map ->
            aMap = map
            aMap!!.setOnMapLongClickListener { point ->
                Log.e(
                    "mapcore",
                    "onMapLongClick $point"
                )
            }
            addMarkersToMap() // 往地图上添加marker
        }

        val expressId = intent.getStringExtra("express_id")
        if (expressId != null) {
            findViewById<TextView>(R.id.express_id).text = expressId
            showExpressDetails(expressId)
        }


        val bottomSheet: View = findViewById(R.id.bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //这里是bottomSheet状态的改变
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        })
    }

    private fun showExpressDetails(id: String) {
        val client: OkHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
        val request: Request = Request
            .Builder()
            .header("Authorization", "APPCODE 6515cd7e7b7648de91332d24a8ce85a0")
            .url("https://kuaidi100.market.alicloudapi.com/getExpress?NO=$id&TYPE=AUTO")
            .get()
            .build()
        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP  125", "ERRORvsdkujvhsdajikvhsdvhjksdv:${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                Log.e("OKHTTP  126", "get response: $responseData")
                val gson = Gson()
                val express100Return = gson.fromJson(responseData, Express100Return::class.java)
                expressInformationList = express100Return.list!!
                handler.post {
                    updateExpressList()
                }
            }
        })
    }

    fun updateExpressList() {
        val recycleView = findViewById<RecyclerView>(R.id.express_detail_list)
        recycleView.layoutManager = LinearLayoutManager(context)
        recycleView.adapter = ExpressDetailAdapter()
    }
    inner class ExpressDetailAdapter : RecyclerView.Adapter<ExpressDetailAdapter.ViewHolder>() {
        inner class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
            val time : TextView = view.findViewById(R.id.time)
            val content : TextView = view.findViewById(R.id.content)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.express_detail_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val express = expressInformationList[position]
            holder.time.text = express.time
            holder.content.text = express.content
        }

        override fun getItemCount(): Int {
            return expressInformationList.size
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