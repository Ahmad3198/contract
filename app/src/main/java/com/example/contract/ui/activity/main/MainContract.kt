package com.example.contract.ui.activity.main

import android.widget.LinearLayout
import com.example.contract.ui.BaseContract

class MainContract {

    interface View: BaseContract.View {
        fun showListImage()
        fun hideListImage()
    }

    interface Presenter: BaseContract.Presenter<MainContract.View> {
        fun onDrawerExpandableLayout(view: LinearLayout, isExpand: Boolean)
    }
}