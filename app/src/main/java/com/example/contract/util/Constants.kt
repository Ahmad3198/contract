package com.example.contract.util

import android.R.attr.y
import android.R.attr.x
import android.content.Context
import android.graphics.Point
import android.view.Display
import androidx.appcompat.app.AppCompatActivity
import android.util.DisplayMetrics




class Constants {

    fun getScreenDisplay(activity: AppCompatActivity) : DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    companion object {
        const val BASE_URL = "https://jsonplaceholder.typicode.com/"
        const val KAFKA_URL = "10.0.120.81:9092"
    }
}