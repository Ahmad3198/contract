package com.example.mvvmkotlin.model

import com.example.mvvmkotlin.view.gallery.GalleryActivity
import java.util.*


data class PathImage(
    var type: GalleryActivity.MediaType,
    var path: String,
    var date: Date,
    var select: Boolean = false

)