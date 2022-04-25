package com.example.meiyong.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.meiyong.ChangePassword
import com.example.meiyong.MapsActivity
import com.example.meiyong.R
import com.example.meiyong.databinding.FragmentDashboardBinding
import http.OkHttp
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn: Button = activity?.findViewById<View>(R.id.button4) as Button
        btn.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            startActivity(intent)
        }

        val btn2: Button = activity?.findViewById(R.id.button_login) as Button
        btn2.setOnClickListener {
            Toast.makeText(activity, "aasdasdas", Toast.LENGTH_LONG).show()
            val jsonObject = JSONObject()
            jsonObject
                .put("username", "11")
                .put("password", "11111111")
            OkHttp.post("/User/loginIn", jsonObject, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    Log.e("OKHTTP_LOGIN","$responseData")
//                    TODO("Not yet implemented")
                }
            })
        }
        val btn3: Button = activity?.findViewById(R.id.button_get) as Button
        btn3.setOnClickListener {
//            OkHttp.get("/Order/1")
            val jsonObject = JSONObject()
            jsonObject
                .put("receiverName", "sqq")
//                .put("deliveryAddress", 1)
                .put("phone", 18910743564)
                .put("packagePos", "南邮驿站")
                .put("stationId", 1)
            OkHttp.post("/Order", jsonObject, object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
//                    TODO("Not yet implemented")
                }
            })
            Toast.makeText(activity, "发起订单请求", Toast.LENGTH_LONG).show()
        }

        activity?.findViewById<Button>(R.id.change_password)!!.setOnClickListener {
            val intent = Intent(activity, ChangePassword::class.java)
            startActivity(intent)
        }

        activity?.findViewById<Button>(R.id.button_get_order)!!.setOnClickListener {
            OkHttp.get("/Order/1")
        }
    }
}