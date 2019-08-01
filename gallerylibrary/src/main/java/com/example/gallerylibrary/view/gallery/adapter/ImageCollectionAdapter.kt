package com.example.mvvmkotlin.view.gallery.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.model.PathImage
import com.example.mvvmkotlin.view.gallery.GalleryActivity
import kotlinx.android.synthetic.main.adapter_folder.view.*
import java.util.ArrayList

class ImageCollectionAdapter : RecyclerView.Adapter<ImageCollectionAdapter.ViewHolder>() {

    lateinit var context: Context
    var images = ArrayList<PathImage>()
    private var selectedImage: SelectedImage? = null
    var selects = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_folder, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun setSelectImage(selectedImage: SelectedImage){
        this.selectedImage = selectedImage
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //set image Folder
        Glide.with(context).load("file://" + images[position].path).into(holder.view.imgFolder)
        holder.view.title.visibility = View.GONE
        holder.view.selected.visibility = View.VISIBLE
        if (images[position].type == GalleryActivity.MediaType.VIDEO) holder.view.imgCenter.visibility = View.VISIBLE else holder.view.imgCenter.visibility = View.GONE

        holder.view.imgFolder.setOnClickListener {
            images[position].select = !images[position].select
            if (images[position].select) {
                holder.view.selected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked))
                selects.add(images[position].path)
                this.selectedImage?.selected()
            } else {
                holder.view.selected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_selected))
                if (selects.indexOf(images[position].path) != -1) {
                    selects.removeAt(selects.indexOf(images[position].path))
                    this.selectedImage?.selected()
                }
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }

    interface SelectedImage {
        fun selected()
    }
}

