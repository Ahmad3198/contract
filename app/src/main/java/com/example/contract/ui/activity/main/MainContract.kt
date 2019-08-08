package com.example.contract.ui.activity.main

import android.widget.LinearLayout
import com.example.contract.ui.BasePresenter

class MainContract : BasePresenter(){

    interface View: BasePresenter.View {
        fun showListImage()
        fun hideListImage()
    }

    interface Presenter: BasePresenter.Presenter<MainContract.View> {
        fun onDrawerExpandableLayout(view: LinearLayout, isExpand: Boolean)
    }
}