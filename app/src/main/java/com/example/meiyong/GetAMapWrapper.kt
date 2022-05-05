package com.example.meiyong


import com.example.meiyong.R
import android.content.Context
import android.view.View
import com.amap.api.maps.AMapWrapper
import com.example.meiyong.webview.MAWebViewWrapper
import com.example.meiyong.webview.MyWebView
import com.google.android.material.navigation.NavigationView


class GetAMapWrapper {
    companion object {
        var webView: MyWebView? = null
        var aMapWrapper: AMapWrapper? = null
    }

    fun getAMapWrapper(context: Context): AMapWrapper {

        val account: View = View.inflate(context, R.layout.activity_gaode_map, null)
        webView = account.findViewById<View>(R.id.gao_de_map) as MyWebView
        val webViewWrapper = MAWebViewWrapper(webView)
        aMapWrapper = AMapWrapper(context, webViewWrapper)



        aMapWrapper = AMapWrapper(context, webViewWrapper)
        return aMapWrapper as AMapWrapper
    }

    fun returnAMapWrapper(context: Context): AMapWrapper? {
        if (aMapWrapper == null) {
            getAMapWrapper(context)
        }
        return aMapWrapper
    }
}