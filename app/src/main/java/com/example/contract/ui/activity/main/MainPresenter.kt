package com.example.contract.ui.activity.main

import android.widget.LinearLayout
import com.example.contract.util.ExpandableLayout
import io.reactivex.disposables.CompositeDisposable

class MainPresenter: MainContract.Presenter, ExpandableLayout.AnimationEnd{

    private val subscriptions = CompositeDisposable()
    private lateinit var view: MainContract.View

    override fun subscribe() {

    }

    override fun unsubscribe() {
        subscriptions.clear()
    }

    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun onDrawerExpandableLayout(view: LinearLayout, isExpand: Boolean) {
        val expandableLayout = ExpandableLayout(view)
        expandableLayout.attach(this)
        if (isExpand) expandableLayout.show() else expandableLayout.hide()
    }

    override fun onShowAnimationEnd() {
        this.view.showListImage()
    }

    override fun onHideAnimationEnd() {
        this.view.hideListImage()
    }

}