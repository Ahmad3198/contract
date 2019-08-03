package com.example.gallerylibrary.ui.gallery

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallerylibrary.R
import com.example.gallerylibrary.model.ImageGallery
import kotlinx.android.synthetic.main.fragment_image_collection.*


class ImageCollectionFragment : Fragment(), ImageCollectionAdapter.SelectedImage {

    private var listener: SelectedCallBack? = null
    lateinit var folder: ImageGallery
    private val imageCollectionAdapter = ImageCollectionAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        imageCollectionAdapter.context = this.context!!
        imageCollectionAdapter.images = folder.path
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_collection, container, false)
    }

    fun setSelectedCallBack(selectedCallBack: SelectedCallBack){
        this.listener = selectedCallBack
    }

    override fun onStart() {
        super.onStart()
        setUpView()
    }

    private fun setUpView(){
        val gridLayout = GridLayoutManager(this.context, 3)
        recycleCollection.layoutManager = gridLayout
        recycleCollection.adapter = imageCollectionAdapter
        imageCollectionAdapter.setSelectImage(this)

        bottomView.setOnClickListener { listener?.success(imageCollectionAdapter.selects) }
    }

    private fun removeFragment() {
        activity!!.supportFragmentManager.popBackStack("ImageCollectionFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        activity!!.supportFragmentManager.beginTransaction()
            .remove(activity!!.supportFragmentManager.findFragmentByTag("ImageCollectionFragment")!!).commit()
    }

    override fun onResume() {
        super.onResume()
        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                removeFragment()
                return@OnKeyListener true
            }
            false
        })
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun selected() {
        txtCount.text = "("+ imageCollectionAdapter.selects.size +")"
    }

    interface SelectedCallBack {
        fun success(images: ArrayList<String>)
    }
}
