package com.example.gallerylibrary.model

import com.example.gallerylibrary.ui.gallery.GalleryActivity
import java.util.*


data class PathImage(
    var type: GalleryActivity.MediaType,
    var path: String,
    var date: Date,
    var select: Boolean = false

)