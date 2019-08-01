package com.example.mvvmkotlin.view.gallery

import com.example.mvvmkotlin.model.ImageGallery
import java.util.ArrayList

sealed class GalleryState (val allImages: ArrayList<ImageGallery>? = null){

    data class Success(private val images : ArrayList<ImageGallery>) : GalleryState(images)
}