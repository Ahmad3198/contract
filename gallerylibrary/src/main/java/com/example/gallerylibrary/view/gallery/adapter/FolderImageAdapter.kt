package com.example.mvvmkotlin.view.gallery.adapter

import android.content.Context

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.model.ImageGallery
import kotlinx.android.synthetic.main.adapter_folder.view.*
import java.util.ArrayList
import android.hardware.Camera
import android.view.*


class FolderImageAdapter : RecyclerView.Adapter<FolderImageAdapter.ViewHolder>(), SurfaceHolder.Callback {

    lateinit var context: Context
    var allFolder = ArrayList<ImageGallery>()
    private var selectFolder: SelectFolder? = null
    var camera: Camera? = null
    var inPreview = false
    private var cameraConfigured = false
    private lateinit var previewHolder : SurfaceHolder

    fun setSelectFolder(selectFolder: SelectFolder) {
        this.selectFolder = selectFolder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.adapter_folder, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return allFolder.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if (position != 0) {
            holder.view.txtFolder.text = allFolder[position].folder
            holder.view.txtFolderSize.text = allFolder[position].path.size.toString()
            holder.view.imgFolder.visibility = View.VISIBLE
            holder.view.cameraPreview.visibility = View.GONE
            //set image Folder
            Glide.with(context).load("file://" + allFolder[position].path.first().path).into(holder.view.imgFolder)
        } else {
            startPreview()
            holder.view.cameraPreview.visibility = View.VISIBLE
            previewHolder = holder.view.cameraPreview.holder
            holder.view.cameraPreview.holder.addCallback(this)
            holder.view.imgFolder.visibility = View.GONE
            holder.view.title.visibility = View.GONE
            holder.view.imgCenter.visibility = View.VISIBLE
            holder.view.imgCenter.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_camera))
        }

        holder.view.cameraPreview.setOnClickListener { selectFolder?.selected(position) }
        holder.view.imgFolder.setOnClickListener { selectFolder?.selected(position) }
    }

    private fun getBestPreviewSize(width: Int, height: Int, parameters: Camera.Parameters): Camera.Size? {
        var result: Camera.Size? = null

        for (size in parameters.supportedPreviewSizes) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size
                } else {
                    val resultArea = result.width * result.height
                    val newArea = size.width * size.height

                    if (newArea > resultArea) {
                        result = size
                    }
                }
            }
        }

        return result
    }

    private fun initPreview(width: Int, height: Int) {
        if (camera != null && previewHolder.surface != null) {
            try {
                camera!!.setPreviewDisplay(previewHolder)
            } catch (t: Throwable) {
                t.printStackTrace()
            }

            if (!cameraConfigured) {
                val parameters = camera!!.parameters
                val size = getBestPreviewSize(
                    width, height,
                    parameters
                )

                if (size != null) {
                    parameters.setPreviewSize(size!!.width, size!!.height)
                    camera!!.parameters = parameters
                    cameraConfigured = true
                }
            }
        }
    }

    fun startPreview() {
        if (cameraConfigured && camera != null) {
            camera!!.startPreview()
            inPreview = true
        }
    }

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {
        initPreview(p2, p3)
        startPreview()
    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {

    }

    override fun surfaceCreated(p0: SurfaceHolder?) {

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var view = itemView
    }

    interface SelectFolder {
        fun selected(index: Int)
    }
}

