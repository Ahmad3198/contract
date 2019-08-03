package com.example.contract.ui.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.contract.R
import com.example.contract.di.component.DaggerActivityComponent
import com.example.contract.di.module.ActivityModule
import com.example.gallerylibrary.manager.GalleryManager
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GalleryManager.CallBack {

    @Inject lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()

        openGallery.setOnClickListener {
            GalleryManager(this).open()
        }
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
        activityComponent.inject(this)
    }

    override fun resultPath(arrayList: ArrayList<String>) {

    }
}
