package com.example.gallerylibrary.ui.gallery

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gallerylibrary.BuildConfig
import com.example.gallerylibrary.R
import com.example.gallerylibrary.model.ImageGallery
import com.example.gallerylibrary.model.PathImage
import com.example.gallerylibrary.ui.gallery.adapter.FolderImageAdapter
import com.example.gallerylibrary.util.MediaStoreContent
import kotlinx.android.synthetic.main.activity_gallery.*
import rx_activity_result2.RxActivityResult
import java.io.File
import java.text.SimpleDateFormat
import java.util.*



class GalleryActivity : AppCompatActivity(), FolderImageAdapter.SelectFolder, ImageCollectionFragment.SelectedCallBack {

    enum class MediaType {
        IMAGE
        ,
        VIDEO
        ,
        ALL
    }
    private lateinit var mediaStoreContent : MediaStoreContent
    private val REQUEST_PERMISSIONS = 100
    private val folderImageAdapter = FolderImageAdapter()
    var allImages = ArrayList<ImageGallery>()
    var mediaType: MediaType = MediaType.ALL
    private var images: ArrayList<String> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        mediaStoreContent = MediaStoreContent(this)
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
//        folderImageAdapter.notifyDataSetChanged()
//        folderImageAdapter.camera = Camera.open()
//        folderImageAdapter.startPreview()
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
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) && ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
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
                MediaType.VIDEO -> allImages.addAll(mediaStoreContent.findVideo())
                MediaType.IMAGE -> allImages.addAll(mediaStoreContent.findImage())
                MediaType.ALL -> {
                    allImages.addAll(mediaStoreContent.findVideo())
                    allImages.addAll(mediaStoreContent.findImage())
                }
            }
            allImages.forEach { it.path.sortByDescending { it -> it.date } }
            folderImageAdapter.notifyDataSetChanged()
        }
    }

    override fun onPause() {
        super.onPause()
//        folderImageAdapter.camera?.stopPreview()
//        folderImageAdapter.camera?.release()
//        folderImageAdapter.camera = null
//        folderImageAdapter.inPreview = false
//        folderImageAdapter.notifyDataSetChanged()
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
            System.out.println("Path" + BuildConfig.APPLICATION_ID + ".fileprovider")
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
                    val uriImage = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", fileImage)
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriImage)

                    val takeVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                    val imageFileNameVideo = "V$timeStamp.mp4"
                    val fileVideo = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), imageFileNameVideo)

                    val uriVideo = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider", fileVideo)
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
