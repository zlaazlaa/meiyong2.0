package com.example.meiyong

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback


class bottom_sheet_test : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_test)
        val bottomSheet: View = findViewById(R.id.test_bottom_sheet)
        val behavior = BottomSheetBehavior.from<View>(bottomSheet)
        behavior.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //这里是bottomSheet状态的改变
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                //这里是拖拽中的回调，根据slideOffset可以做一些动画
            }
        })
    }
}