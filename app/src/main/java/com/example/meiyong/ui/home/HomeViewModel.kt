package com.example.meiyong.ui.home

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meiyong.R
import com.example.meiyong.StudyjsonData
import com.example.meiyong.StudyjsonExpressData
import com.google.gson.Gson
import http.okhttp
import okhttp3.Call
import okhttp3.Response
import java.io.IOException

class HomeViewModel : ViewModel() {
    val StudyAPIurl =
        "http://123.56.232.18:8080/serverdemo/tag/queryTagList?offset=1231321&pageCount=10&tagId=3&tagType=all&userId=33"

    private var expressList = ArrayList<StudyjsonExpressData>()

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

}