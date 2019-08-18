package com.example.contract.ui.activity.main

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contract.util.ExpandableLayout
import io.reactivex.disposables.CompositeDisposable

 open class MainPresenter: MainContract.Presenter, ExpandableLayout.AnimationEnd{

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

    override fun onDrawerExpandableLayout(activity: AppCompatActivity, view: RecyclerView, isExpand: Boolean) {
        val expandableLayout = ExpandableLayout(view)
        expandableLayout.attach(this)
        expandableLayout.expandOrCollapse(activity,isExpand)
    }

    override fun onShowAnimationEnd() {
        this.view.showListImage()
    }

    override fun onHideAnimationEnd() {
        this.view.hideListImage()
    }

}