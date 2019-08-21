package com.example.contract.ui.activity.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contract.R
import com.example.contract.service.KafkaConnection
import com.example.contract.di.component.DaggerActivityComponent
import com.example.contract.di.module.ActivityModule
import com.example.contract.ui.base.AnimationClose
import com.example.contract.ui.base.BaseActivity
import com.example.gallerylibrary.manager.GalleryManager
import com.example.gallerylibrary.ui.gallery.ImageCollectionFragment
import com.example.contract.ui.adapter.PhotoCollectionAdapter
import com.example.gallerylibrary.util.MediaStoreContent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbarApp
import kotlinx.android.synthetic.main.toolbar_app.view.*
import javax.inject.Inject


class MainActivity : BaseActivity(), GalleryManager.CallBack, MainContract.View,
    ImageCollectionFragment.SelectedCallBack, PhotoCollectionAdapter.SelectedImage{

    @Inject
    lateinit var presenter : MainContract.Presenter
    @Inject
    lateinit var photoCollectionAdapter : PhotoCollectionAdapter
    @Inject
    lateinit var userPresenter : UserPresenter
    @Inject
    lateinit var kafkaConnection: KafkaConnection
    var isExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        presenter.attach(this)
        setActionView()
        setActionBar()
        userPresenter.getAll()
        kafkaConnection.setProperties()
    }

    private fun injectDependency() {
        val activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .build()
        activityComponent.inject(this)
    }

    private fun setActionView() {
        selectImage.setOnClickListener {
            isExpand = !isExpand
            presenter.onDrawerExpandableLayout(this, imageCollection, isExpand)
            if (isExpand) {
                selectImage.setImageDrawable(resources.getDrawable(R.drawable.ic_gallery_red))
                editTextSend.clearFocus()
            }else {
                selectImage.setImageDrawable(resources.getDrawable(R.drawable.ic_gallery))
            }
        }

        editTextSend.setOnFocusChangeListener { view, b ->
            if (b){
               selectImage.callOnClick()
            }
        }
    }

    private fun setActionBar() {
        toolbarApp.title.text = intent.getStringExtra("userName")
        toolbarApp.leftIcon.setImageDrawable(resources.getDrawable(R.drawable.ic_arrow_left))
        toolbarApp.rightIconFirst.setImageDrawable(resources.getDrawable(R.drawable.ic_search))
        toolbarApp.rightIconTwo.visibility = View.GONE
        closeActivity(toolbarApp.leftIcon, AnimationClose.LEFT_TO_RIGHT)
    }

    override fun resultPath(arrayList: ArrayList<String>) {
        Toast.makeText(this, arrayList.first(), Toast.LENGTH_LONG).show()
    }

    override fun showListImage() {
        val gridLayout = GridLayoutManager(this, 3)
        photoCollectionAdapter.context = this
        photoCollectionAdapter.setSelectImage(this)
        imageCollection.layoutManager = gridLayout
        imageCollection.adapter = photoCollectionAdapter
        photoCollectionAdapter.images.addAll(MediaStoreContent(this).findAll().first().path)
        photoCollectionAdapter.notifyDataSetChanged()
    }

    override fun hideListImage() {
    }

    override fun selected(count: Int) {
        //count select for update UI
    }

    override fun photoClick(count: Int) {
        Log.d("Photo Select", count.toString())
    }

    override fun success(images: ArrayList<String>) {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        this.overrideTransitionLeftTORight()
    }
}
