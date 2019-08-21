package com.example.contract.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contract.R
import com.example.gallerylibrary.model.PathImage
import com.example.gallerylibrary.ui.gallery.GalleryActivity
import kotlinx.android.synthetic.main.item_photo.view.*


class PhotoCollectionAdapter : RecyclerView.Adapter<PhotoCollectionAdapter.ViewHolder>() {

    lateinit var context: Context
    var images = ArrayList<PathImage>()
    private var selectedImage: SelectedImage? = null
    var selects = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false)
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
        Glide.with(context).load("file://" + images[position].path).into(holder.view.imageDisplay)
        holder.view.selected.visibility = View.GONE
        if (images[position].type == GalleryActivity.MediaType.VIDEO)
            holder.view.imgCenter.visibility = View.VISIBLE
        else holder.view.imgCenter.visibility = View.GONE

        holder.view.selected.setOnClickListener {
            images[position].select = !images[position].select
            if (images[position].select) {
                holder.view.selected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_checked))
                selects.add(images[position].path)
                this.selectedImage?.selected(selects.size)
            } else {
                holder.view.selected.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.shape_selected))
                if (selects.indexOf(images[position].path) != -1) {
                    selects.removeAt(selects.indexOf(images[position].path))
                    this.selectedImage?.selected(selects.size)
                }
            }
        }

        holder.view.imageDisplay.setOnClickListener {
            this.selectedImage?.photoClick(selects.size)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }

    interface SelectedImage {
        fun selected(count: Int)
        fun photoClick(count: Int)
    }
}

