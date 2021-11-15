package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.fragment.NextFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding
    lateinit var navController : NavController
    private lateinit var nextFragment :NextFragment
    val REQUEST_IMAGE_CAPTURE=1
    lateinit var currentPhotoPath: String


    fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
    }

    fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission:"+permissions[0]+"was"+grantResults[0]+"카메라 허가 받음")
        } else {
            Log.d("TAG", "카메라 허가 못 받음")
        }
    }

    fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.packageManager)!=null ) {
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "error occurred during creating image file")
                        null
                    }
                if (Build.VERSION.SDK_INT<24) {
                    if (photoFile!=null){
                        val photoURI=Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
                    }
                } else {
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile (
                            this, "com.example.myapplication", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }

    fun createImageFile(): File {
        val timeStamp: String= SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File?= getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /*prefix*/
            ".jpg", /*suffix*/
            storageDir /*directory*/
        ).apply {
            currentPhotoPath=absolutePath
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }



    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {

        return super.onCreateView(name, context, attrs)

    }

}




//        /*detail.setOnClickListener({
//            val intent = Intent(this, MainFragment::class.java)
//            startActivity(intent)
//        })*/
//
//
//    }
//
//}




