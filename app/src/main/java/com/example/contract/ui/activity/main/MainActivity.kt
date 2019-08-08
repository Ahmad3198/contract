package com.example.contract.ui.activity.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.contract.R
import com.example.contract.service.KafkaConnection
import com.example.contract.di.component.DaggerActivityComponent
import com.example.contract.di.module.ActivityModule
import com.example.contract.service.RealmUser
import com.example.gallerylibrary.manager.GalleryManager
import com.example.gallerylibrary.ui.gallery.ImageCollectionAdapter
import com.example.gallerylibrary.ui.gallery.ImageCollectionFragment
import com.example.gallerylibrary.util.MediaStoreContent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity(), GalleryManager.CallBack, MainContract.View,
    ImageCollectionFragment.SelectedCallBack, ImageCollectionAdapter.SelectedImage{

    @Inject
    lateinit var presenter : MainContract.Presenter
    @Inject
    lateinit var imageCollectionAdapter : ImageCollectionAdapter
    @Inject
    lateinit var userPresenter : UserPresenter
    var isExpand = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        injectDependency()
        presenter.attach(this)
        setActionView()

        userPresenter.getAll()

        KafkaConnection()
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
            presenter.onDrawerExpandableLayout(frameImageSelect, isExpand)
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

    override fun resultPath(arrayList: ArrayList<String>) {
        Toast.makeText(this, arrayList.first(), Toast.LENGTH_LONG).show()
    }

    override fun showListImage() {
        imageCollection.visibility = View.VISIBLE
        val gridLayout = GridLayoutManager(this, 3)
        imageCollectionAdapter.context = this
        imageCollectionAdapter.setSelectImage(this)
        imageCollection.layoutManager = gridLayout
        imageCollection.adapter = imageCollectionAdapter
        imageCollectionAdapter.images.addAll(MediaStoreContent(this).findAll().first().path)
        imageCollectionAdapter.notifyDataSetChanged()
    }

    override fun hideListImage() {
        imageCollection.visibility = View.GONE
    }


    override fun selected(count: Int) {
        //count select for update UI
    }

    override fun success(images: ArrayList<String>) {

    }

}
