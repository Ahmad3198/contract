package com.example.contract

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmkotlin.view.GalleryManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GalleryManager.CallBack {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openGallery.setOnClickListener {
            GalleryManager(this).open();
        }
    }

    override fun resultPath(arrayList: ArrayList<String>) {

    }
}
