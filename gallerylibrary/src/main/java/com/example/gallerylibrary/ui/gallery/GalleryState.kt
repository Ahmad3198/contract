package com.example.gallerylibrary

import com.example.gallerylibrary.model.ImageGallery
import java.util.ArrayList

sealed class GalleryState (val allImages: ArrayList<ImageGallery>? = null){

    data class Success(private val images : ArrayList<ImageGallery>) : GalleryState(images)
}