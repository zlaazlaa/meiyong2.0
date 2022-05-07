package com.example.meiyong.ui.notifications

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.meiyong.ManageOrders
import com.example.meiyong.R
import com.example.meiyong.data.LoginRepository
import com.example.meiyong.databinding.FragmentNotificationsBinding
import com.example.meiyong.ui.login.LoginActivity

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
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

        val btn: Button = view.findViewById<View>(R.id.button3) as Button
        btn.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<LinearLayout>(R.id.list1)?.setOnClickListener {
            val intent = Intent(activity, ManageOrders::class.java)
            intent.putExtra("order_type", "1")
            startActivity(intent)
        }

        view?.findViewById<LinearLayout>(R.id.list2)?.setOnClickListener {
            val intent = Intent(activity, ManageOrders::class.java)
            intent.putExtra("order_type", "2")
            startActivity(intent)
        }
    }
}