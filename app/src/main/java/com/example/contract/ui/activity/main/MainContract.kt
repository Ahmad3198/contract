package com.example.contract.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contract.ui.BasePresenter

class MainContract : BasePresenter(){

    interface View: BasePresenter.View {
        fun showListImage()
        fun hideListImage()
    }

    interface Presenter: BasePresenter.Presenter<MainContract.View> {
        fun onDrawerExpandableLayout(
            activity: AppCompatActivity,
            view: RecyclerView, isExpand: Boolean)
    }
}