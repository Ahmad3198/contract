package com.example.mvvmkotlin.view.gallery

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.database.CursorJoiner
import android.hardware.Camera
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.ContactsContract
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmkotlin.BuildConfig
import com.example.mvvmkotlin.R
import com.example.mvvmkotlin.model.ImageGallery
import com.example.mvvmkotlin.model.PathImage
import com.example.mvvmkotlin.view.gallery.adapter.FolderImageAdapter
import kotlinx.android.synthetic.main.activity_gallery.*
import rx_activity_result2.RxActivityResult
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class GalleryActivity : AppCompatActivity(), FolderImageAdapter.SelectFolder, ImageCollectionFragment.SelectedCallBack {

    enum class MediaType {
        IMAGE
        ,
        VIDEO
        ,
        ALL
    }

    private val REQUEST_PERMISSIONS = 100
    private val folderImageAdapter = FolderImageAdapter()
    var allImages = ArrayList<ImageGallery>()
    var mediaType: MediaType = MediaType.ALL
    private var images: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        this.setUpView()
        this.getImage()
    }

    private fun setUpView() {
        val gridLayout = GridLayoutManager(this, 3)
        folderImageAdapter.context = this
        folderImageAdapter.allFolder = allImages
        recycleContentFolder.layoutManager = gridLayout
        recycleContentFolder.adapter = folderImageAdapter
        folderImageAdapter.setSelectFolder(this)
    }

    override fun onResume() {
        super.onResume()
        folderImageAdapter.camera = Camera.open()
        folderImageAdapter.startPreview()
        folderImageAdapter.notifyDataSetChanged()
    }

    private fun getImage() {

        @Suppress("DEPRECATED_IDENTITY_EQUALS")
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            if (
                ActivityCompat.shouldShowRequestPermissionRationale(
                    this@GalleryActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this@GalleryActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    this@GalleryActivity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            allImages.clear()
            allImages.add(0, ImageGallery("", ArrayList()))
            when (mediaType) {
                MediaType.VIDEO -> findVideo()
                MediaType.IMAGE -> findImage()
                MediaType.ALL -> {
                    findVideo()
                    findImage()
                }
            }
            allImages.forEach { it.path.sortByDescending { it -> it.date } }
            folderImageAdapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        if (folderImageAdapter.inPreview) {
            folderImageAdapter.camera!!.stopPreview()
        }
        folderImageAdapter.camera!!.release()
        folderImageAdapter.camera = null
        folderImageAdapter.inPreview = false
        super.onPause()
    }

    private fun findVideo() {

        val uri1 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val orderBy = MediaStore.Video.Media.DATE_TAKEN
        val projectionVideo = arrayOf(MediaStore.Video.Media.DATA, MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)
        val cursorVideo = applicationContext.contentResolver.query(uri1, projectionVideo, null, null, "$orderBy DESC")!!
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
                allImages[index].path.add(PathImage(MediaType.VIDEO, absolutePathOfVideo, Date(dateTaken.toLong())))
            } else {
                val arrayList = ArrayList<PathImage>()
                arrayList.add(PathImage(MediaType.VIDEO, absolutePathOfVideo, Date(dateTaken.toLong())))
                allImages.add(ImageGallery(cursorVideo.getString(columnIndexFolderNameVideo), arrayList))
            }
        }
    }

    private fun findImage(){

        val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media.DATE_TAKEN)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN
        val cursor = applicationContext.contentResolver.query(uri, projection, null, null, "$orderBy DESC")!!
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
                allImages[index].path.add(PathImage(MediaType.IMAGE, absolutePathOfImage, Date(dateTaken.toLong())))
            } else {
                val arrayList = ArrayList<PathImage>()
                arrayList.add(PathImage(MediaType.IMAGE, absolutePathOfImage, Date(dateTaken.toLong())))
                allImages.add(ImageGallery(cursor.getString(columnIndexFolderName), arrayList))
            }
        }
    }

    override fun selected(index: Int) {
        if (index > 0) {

            val imageCollectionFragment = ImageCollectionFragment()
            imageCollectionFragment.setSelectedCallBack(this)
            imageCollectionFragment.folder = allImages[index]
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, imageCollectionFragment, "ImageCollectionFragment")
            transaction.commit()
        } else {

            val timeStamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            when (mediaType) {
                MediaType.IMAGE -> {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val imageFileName = "IMG$timeStamp.jpg"
                    val fileImage = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName)
                    val uriImage = FileProvider.getUriForFile(this@GalleryActivity, BuildConfig.APPLICATION_ID + ".fileprovider", fileImage)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)
                    RxActivityResult.on(this).startIntent(takePictureIntent).subscribe {
                        val resultCode = it.resultCode()
                        if (resultCode == Activity.RESULT_OK) {
                            System.out.println("IMG OK")
                            val image = getImageContent(fileImage, "jpeg")
                            val cr = contentResolver
                            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image)
                            this.getImage()
                        }else {
                            System.out.println("IMG ERROR")
                        }
                    }
                }

                MediaType.VIDEO -> {
                    val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    val imageFileNameVideo = "V$timeStamp.mp4"
                    val fileVideo = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileNameVideo)
                    val uriVideo = FileProvider.getUriForFile(this@GalleryActivity, BuildConfig.APPLICATION_ID + ".fileprovider", fileVideo)
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriVideo)
                    RxActivityResult.on(this).startIntent(takeVideoIntent).subscribe {
                        val resultCode = it.resultCode()
                        if (resultCode == Activity.RESULT_OK) {
                            System.out.println("Video OK")
                            val image = getVideoContent(fileVideo, "mp4")
                            val cr = contentResolver
                            cr.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, image)
                            this.getImage()
                        }else {
                            System.out.println("Video ERROR")
                        }
                    }
                }

                MediaType.ALL -> {
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    val imageFileName = "IMG$timeStamp.jpg"
                    val fileImage = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileName)
                    val uriImage = FileProvider.getUriForFile(this@GalleryActivity, BuildConfig.APPLICATION_ID + ".fileprovider", fileImage)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)

                    val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    val imageFileNameVideo = "V$timeStamp.mp4"
                    val fileVideo = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileNameVideo)
                    val uriVideo = FileProvider.getUriForFile(this@GalleryActivity, BuildConfig.APPLICATION_ID + ".fileprovider", fileVideo)
                    takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriVideo)

                    val chooserIntent = Intent.createChooser(takeVideoIntent, "Capture Image or Video")
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(takePictureIntent))
                    RxActivityResult.on(this).startIntent(chooserIntent).subscribe {
                        val resultCode = it.resultCode()
                        if (resultCode == Activity.RESULT_OK && it.data() != null) {
                            System.out.println("Video OK")
                            val image = getVideoContent(fileVideo, "mp4")
                            val cr = contentResolver
                            cr.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, image)
                            this.getImage()
                        }else if (resultCode == Activity.RESULT_OK){
                            System.out.println("IMG OK")
                            val image = getImageContent(fileImage, "jpeg")
                            val cr = contentResolver
                            cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image)
                            this.getImage()
                        }
                    }
                }
            }
        }
    }

    private fun getImageContent(parent: File, type: String): ContentValues {
        val image = ContentValues()
        image.put(MediaStore.Images.Media.DISPLAY_NAME, parent.name)
        image.put(MediaStore.Images.Media.DESCRIPTION, "App Image")
        image.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis())
        image.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        image.put(MediaStore.Images.Media.MIME_TYPE, "image/" + type.toLowerCase())
        image.put(MediaStore.Images.Media.ORIENTATION, 0)
        image.put(MediaStore.Images.ImageColumns.BUCKET_ID, parent.toString().toLowerCase().hashCode())
        image.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, parent.name.toLowerCase())
        image.put(MediaStore.Images.Media.SIZE, parent.length())
        image.put(MediaStore.Images.Media.DATA, parent.absolutePath)
        return image
    }

    private fun getVideoContent(parent: File, type: String): ContentValues {
        val image = ContentValues()
        image.put(MediaStore.Video.Media.DISPLAY_NAME, parent.name)
        image.put(MediaStore.Video.Media.DESCRIPTION, "App Image")
        image.put(MediaStore.Video.Media.DATE_ADDED, System.currentTimeMillis())
        image.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
        image.put(MediaStore.Video.Media.MIME_TYPE, "video/" + type.toLowerCase())
        image.put(MediaStore.Video.Media.SIZE, parent.length())
        image.put(MediaStore.Images.Media.DATA, parent.absolutePath)
        return image
    }

    override fun onBackPressed() {
        val data = Intent()
        data.putExtra("path", this.images)
        setResult(Activity.RESULT_OK, data)
        finish()
    }

    override fun success(images: ArrayList<String>) {
        this.images = images
        if (this.images.size > 0) {
            onBackPressed()
        }
    }
}
