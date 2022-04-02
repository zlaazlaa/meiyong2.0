package com.example.meiyong.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.meiyong.*
import com.example.meiyong.databinding.FragmentHomeBinding
import com.example.meiyong.ReceiveClass.StudyjsonData
import com.example.meiyong.ReceiveClass.StudyjsonExpressData
import com.google.android.material.button.MaterialButton
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator
import http.OkHttp.sendOkHttpRequestGET
import okhttp3.Call
import okhttp3.Response
import java.io.IOException


class HomeFragment : Fragment() {

    val StudyAPIurl =
        "http://123.56.232.18:8080/serverdemo/tag/queryTagList?offset=1231321&pageCount=10&tagId=3&tagType=all&userId=33"
    val SunAPIurl = "http://192.168.0.127:80/Order"

    val PanAPIurl = "http://192.168.0.114:8081/api/tbSendOrder/list"

    val YuAPIurl = "http://192.168.0.112:8081/api/tbPickOrder/list"
    private var expressList = ArrayList<StudyjsonExpressData>()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
//        okhttp.sendOkHttpRequestGET(StudyAPIurl, object : okhttp3.Callback {
//            override fun onResponse(call: Call, response: Response) {
//                val gson = Gson()
//                val responseData = response.body?.string()
////                val typeOf = object : TypeToken<ArrayList<queryOrder>>() {}.type
//                val express_list = gson.fromJson(responseData, StudyjsonData::class.java)
//                expressList.clear()
//                for ((cnt, ex) in express_list.data.data.withIndex()) {
//                    expressList.add(cnt, ex)
//                }
//            }
//
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("OKHTTP", "ERROR")
//            }
//        })
//        val recyclerView: RecyclerView = binding.expressListView
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = CardAdapter()


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val view2: View = requireActivity().layoutInflater.inflate(R.layout.fragment_dashboard, null)


//        recyclerView.setOnTouchListener (object : View.OnTouchListener {
//            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
//                p1.action ==
//            }
//        })
    }

    inner class CardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val img_express_company: ImageView = view.findViewById(R.id.imageView_express_company)
            val txt_express_statement: TextView = view.findViewById(R.id.textView_express_statement)
            val txt_express_number: TextView = view.findViewById(R.id.textView_express_number)
            val btn_operation: Button = view.findViewById(R.id.button_operation)
        }

        @SuppressLint("ResourceAsColor")
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardAdapter.ViewHolder {
            val view =
                LayoutInflater.from(context).inflate(R.layout.express_information, parent, false)
            val viewHolder = ViewHolder(view)
            viewHolder.btn_operation.setOnClickListener {
                if (viewHolder.itemView.findViewById<MaterialButton>(R.id.button_operation).text == "无人车派送") {
                    Toast.makeText(parent.context, "开始安排无人车派送", Toast.LENGTH_SHORT).show()
                    sendOrders(viewHolder.itemView.findViewById<TextView>(R.id.textView_express_number).text.toString())
                    viewHolder.itemView.findViewById<MaterialButton>(R.id.button_operation).text =
                        "验证码取件"
                    viewHolder.itemView.findViewById<MaterialButton>(R.id.button_operation)
                        .setBackgroundColor(
                            Color.parseColor("#02C39A")
                        )
                } else {
                    val intent = Intent(activity, PIN_Code::class.java)
                    startActivity(intent)
                }
            }
            return ViewHolder(view)
        }

        override fun getItemCount() = expressList.size

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

            val express = expressList[position]
            holder.itemView.findViewById<ImageView>(R.id.imageView_express_company)
                .setImageResource(R.drawable.jd)
//            holder.itemView.findViewById<TextView>(R.id.textView_express_statement).text =
//                express.information
//            holder.itemView.findViewById<TextView>(R.id.textView_express_number).text =
//                express.number
//            holder.itemView.findViewById<TextView>(R.id.textView_express_information).text =
//                express.statement
//            if(express.statement == "到达") {
//                holder.itemView.findViewById<Button>(R.id.button_operation).text = "无人车投递"
//            }
//            else if (express.statement == "派送"){
//                holder.itemView.findViewById<Button>(R.id.button_operation).text = "验证码取件"
//            }
            holder.itemView.findViewById<TextView>(R.id.textView_express_statement).text =
                express.title
            holder.itemView.findViewById<TextView>(R.id.textView_express_number).text =
                express.icon.substring(express.icon.length - 15, express.icon.length - 1)
        }
    }

    inner class CardAdapterChangeList : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context)
                .inflate(R.layout.send_express_information, parent, false)
            return ViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            holder.itemView.findViewById<ImageView>(R.id.imageView_express_company)
                .setImageResource(R.drawable.sf)
            holder.itemView.findViewById<TextView>(R.id.textView_express_statement).text =
                "${position + 1}号包裹"
            holder.itemView.findViewById<TextView>(R.id.textView_express_number).text =
                "SF27468712412451"
            holder.itemView.findViewById<TextView>(R.id.textView_express_information).text =
                "寄给***的包裹"
        }

        override fun getItemCount(): Int {
            return 1000
        }

    }

    private var isGetData = false

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        //   进入当前Fragment
//        if (enter && !isGetData) {
//            isGetData = true
//            getExpressList()
//        } else {
//            isGetData = false
//        }
        getExpressList()
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    override fun onResume() {
        super.onResume()
        getExpressList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden)
        getExpressList()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getExpressList() {
        sendOkHttpRequestGET(StudyAPIurl, object : okhttp3.Callback {
            override fun onResponse(call: Call, response: Response) {
                val gson = Gson()
                val responseData = response.body?.string()
//                val typeOf = object : TypeToken<ArrayList<queryOrder>>() {}.type
                Log.e("OKHTTP","$responseData")
                val express_list = gson.fromJson(responseData, StudyjsonData::class.java)
                expressList.clear()
                for ((cnt, ex) in express_list.data.data.withIndex()) {
                    expressList.add(cnt, ex)
                }

//                val express_list = gson.fromJson(responseData, queryOrder::class.java)
//                for ((cnt, ex) in express_list) {
//                    expressList.add(cnt, ex)
//                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("OKHTTP", "ERROR")
            }
        })
        val recyclerView: RecyclerView =
            view?.findViewById<View>(R.id.express_list_View) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CardAdapter()
        (recyclerView.adapter as CardAdapter).notifyDataSetChanged()
    }


    ///////next is SCAN QR CODE
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //get express list from server
        //Scan QR code
        val btnScan: MaterialButton = activity?.findViewById<View>(R.id.btn1) as MaterialButton
        btnScan.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val integrator = IntentIntegrator.forSupportFragment(this@HomeFragment)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
                integrator.captureActivity = ScanActivity::class.java
                integrator.setPrompt("将二维码放到框内即可") //底部的提示文字，设为""可以置空
                integrator.setCameraId(0) //前置或者后置摄像头
                integrator.setBeepEnabled(true) //扫描成功的「哔哔」声，默认开启
                integrator.setBarcodeImageEnabled(true)
                integrator.initiateScan()
            }
        })

        val btnGetNumber: MaterialButton = activity?.findViewById<View>(R.id.btn3) as MaterialButton
        btnGetNumber.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(activity, PIN_Code::class.java)
                startActivity(intent)
            }
        })

        //Refresh express list
        val btnChangeList: MaterialButton =
            activity?.findViewById<View>(R.id.change_list) as MaterialButton
        btnChangeList.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                getExpressList()
                val bt1: MaterialButton =
                    activity?.findViewById<View>(R.id.change_list) as MaterialButton
                val bt2: MaterialButton =
                    activity?.findViewById<View>(R.id.change_list2) as MaterialButton
                bt1.setTextAppearance(R.style.BigButton)
                bt2.setTextAppearance(R.style.SmallButton)
                val line1: ImageView = activity?.findViewById<View>(R.id.line1) as ImageView
                val line2: ImageView = activity?.findViewById<View>(R.id.line2) as ImageView
                line1.setTransitionVisibility(View.VISIBLE)
                line2.setTransitionVisibility(View.INVISIBLE)

            }
        })

        //Refresh express list2
        val btnChangeList2: MaterialButton =
            activity?.findViewById<View>(R.id.change_list2) as MaterialButton
        btnChangeList2.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val recyclerView: RecyclerView =
                    view?.findViewById<View>(R.id.express_list_View) as RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.adapter = CardAdapterChangeList()
                val bt1: MaterialButton =
                    activity?.findViewById<View>(R.id.change_list) as MaterialButton
                val bt2: MaterialButton =
                    activity?.findViewById<View>(R.id.change_list2) as MaterialButton
                bt1.setTextAppearance(R.style.SmallButton)
                bt2.setTextAppearance(R.style.BigButton)
                val line1: ImageView = activity?.findViewById<View>(R.id.line1) as ImageView
                val line2: ImageView = activity?.findViewById<View>(R.id.line2) as ImageView
                line1.setTransitionVisibility(View.INVISIBLE)
                line2.setTransitionVisibility(View.VISIBLE)
            }
        })

        val btnSendExpress : MaterialButton = activity?.findViewById(R.id.btn2) as MaterialButton
        btnSendExpress.setOnClickListener {
            val intent = Intent(activity, SendExpress::class.java)
            startActivity(intent)
        }
    }

    fun sendOrders(expressNumber: String) {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "扫码取消！", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "扫描成功，条码值: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}