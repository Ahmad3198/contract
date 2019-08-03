package com.example.gallerylibrary.util

import android.content.Context
import android.provider.MediaStore
import com.example.gallerylibrary.model.ImageGallery
import com.example.gallerylibrary.model.PathImage
import com.example.gallerylibrary.ui.gallery.GalleryActivity
import java.util.*

class MediaStoreContent(applicationContext: Context) {

    var context: Context = applicationContext

    fun findVideo() : ArrayList<ImageGallery> {
        var allImages = ArrayList<ImageGallery>()
        val uri1 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val orderBy = MediaStore.Video.Media.DATE_TAKEN
        val projectionVideo = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)
        val cursorVideo = context.contentResolver.query(uri1, projectionVideo, null, null, "$orderBy DESC")!!
        val columnIndexDataVideo = cursorVideo.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
        val columnIndexFolderNameVideo = cursorVideo.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)
        val columnDate = cursorVideo.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
        while (cursorVideo.moveToNext()) {
            var index = 0
            var folderExist = false
            val absolutePathOfVideo = cursorVideo.getString(columnIndexDataVideo)
            val dateTaken = cursorVideo.getString(columnDate)

            for (i: Int in allImages.indices) {
                if (allImages[i].folder == cursorVideo.getString(columnIndexFolderNameVideo)) {
                    folderExist = true
                    index = i
                    break
                } else {
                    folderExist = false
                }
            }

            if (folderExist) {
                allImages[index].path.add(PathImage(GalleryActivity.MediaType.VIDEO, absolutePathOfVideo, Date(dateTaken.toLong())))
            } else {
                val arrayList = ArrayList<PathImage>()
                arrayList.add(PathImage(GalleryActivity.MediaType.VIDEO, absolutePathOfVideo, Date(dateTaken.toLong())))
                allImages.add(ImageGallery(cursorVideo.getString(columnIndexFolderNameVideo), arrayList))
            }
        }

        return allImages
    }

    fun findImage() : ArrayList<ImageGallery>{
        var allImages = ArrayList<ImageGallery>()
        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor = context.contentResolver.query(uri, projection, null, null, "$orderBy DESC")!!
        val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val columnIndexFolderName = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val columnDate = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN)
        while (cursor.moveToNext()) {
            var index = 0
            var folderExist = false
            val absolutePathOfImage = cursor.getString(columnIndexData)
            val dateTaken = cursor.getString(columnDate)

            for (i: Int in allImages.indices) {
                if (allImages[i].folder == cursor.getString(columnIndexFolderName)) {
                    folderExist = true
                    index = i
                    break
                } else {
                    folderExist = false
                }
            }

            if (folderExist) {
                allImages[index].path.add(PathImage(GalleryActivity.MediaType.IMAGE, absolutePathOfImage, Date(dateTaken.toLong())))
            } else {
                val arrayList = ArrayList<PathImage>()
                arrayList.add(PathImage(GalleryActivity.MediaType.IMAGE, absolutePathOfImage, Date(dateTaken.toLong())))
                allImages.add(ImageGallery(cursor.getString(columnIndexFolderName), arrayList))
            }
        }
        return allImages
    }

}