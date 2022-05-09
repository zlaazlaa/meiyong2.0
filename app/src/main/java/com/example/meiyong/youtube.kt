package com.example.meiyong

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


class youtube : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube)
        // 实例化
        val mWebView = findViewById<View>(R.id.youtube_webview) as WebView

        // 开启javascript 渲染
        mWebView.getSettings().setJavaScriptEnabled(true)
        mWebView.setWebViewClient(WebViewClient())

        // 载入内容
        mWebView.loadUrl("file:///android_asset/youtube.html")

//  测试远程的    mWebView.loadUrl("http://www.itxdl.cn");
    }

}
