package com.example.contract.util

import android.graphics.Color
import android.os.CountDownTimer
import android.view.View


class CustomView {

    fun isHighlightSelected(view: View){
        view.setBackgroundColor(Color.LTGRAY)
        val cdt = object : CountDownTimer(100, 50) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                view.setBackgroundColor(Color.WHITE)
            }
        }.start()
    }
}