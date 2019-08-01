package com.example.mvvmkotlin.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mvvmkotlin.view.gallery.GalleryActivity
import rx_activity_result2.RxActivityResult
import java.util.*

class GalleryManager {

    private lateinit var activity: Activity
    private lateinit var fragment: Fragment
    private var callBack: CallBack? = null

    constructor(activity: Activity) {
        this.activity = activity
    }

    constructor(fragment: Fragment) {
        this.fragment = fragment
    }

    fun setCallback(callBack: CallBack) : GalleryManager{
        this.callBack = callBack
        return this
    }

    fun open(): GalleryManager {
        if (activity != null) {
            val galleryActivity = Intent(activity,GalleryActivity::class.java)
            RxActivityResult.on(activity).startIntent(galleryActivity).subscribe {
                val resultCode = it.resultCode()
                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("Gallery Success")
                    val arrayList = it.data().extras!!.getStringArrayList("path")
                    callBack!!.resultPath(arrayList!!)
                }
            }
        } else {
            val galleryActivity = Intent(fragment.activity,GalleryActivity::class.java)
            RxActivityResult.on(fragment.activity).startIntent(galleryActivity).subscribe {
                val resultCode = it.resultCode()
                if (resultCode == Activity.RESULT_OK) {
                    System.out.println("Gallery Success")
                    val arrayList = it.data().extras!!.getStringArrayList("path")
                    callBack!!.resultPath(arrayList!!)
                }
            }
        }
        return  this
    }

    interface CallBack {
        fun resultPath(arrayList: ArrayList<String>)
    }
}