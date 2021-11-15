package com.example.myapplication.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_next.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class NextFragment : Fragment() {

    var mImageView: ImageView? = null
    val REQUEST_IMAGE_CAPTURE=1
    lateinit var currentPhotoPath: String
    val Fragment.packageManager get() = activity?.packageManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_next, container, false)
        mImageView=view.findViewById(R.id.imageView7)
        view.findViewById<Button>(R.id.Searchbutton).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_nextFragment_to_searchFragment)
        }

        return view
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        ), REQUEST_IMAGE_CAPTURE)
    }

    private fun checkPermission(): Boolean {
        return (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)==
                PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        )== PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d("TAG", "Permission:"+permissions[0]+"was"+grantResults[0]+"camera permitted")
        } else {
            Log.d("TAG", "camera not permitted")
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            if (takePictureIntent.resolveActivity(this.packageManager!!) != null) {
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "error occurred during creating image file")
                        null
                    }
                if (Build.VERSION.SDK_INT < 24) {
                    if (photoFile != null) {
                        val photoURI = Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                } else {
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            requireContext(), "com.example.myapplication", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                    }
                }
            }
        }
    }


    private fun createImageFile(): File {
        val timeStamp: String= SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File?= activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /*prefix*/
            ".jpg", /*suffix*/
            storageDir /*directory*/
        ).apply {
            currentPhotoPath=absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){
            1 -> {
                if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){

                    // 카메라로부터 받은 데이터가 있을경우에만
                    val file = File(currentPhotoPath)
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media
                            .getBitmap(activity?.contentResolver, Uri.fromFile(file))  //Deprecated
                        mImageView?.setImageBitmap(bitmap)
                    }
                    else{
                        val decode = ImageDecoder.createSource(
                            activity?.contentResolver!!,
                            Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        mImageView?.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UploadPicture.setOnClickListener {
            if ( (activity as MainActivity).checkPermission() ){
                (activity as MainActivity).dispatchTakePictureIntent()
            }
            else {
                (activity as MainActivity).requestPermission()
            }
        }

        ButtonCategory.setOnClickListener {
            val popupMenu = PopupMenu(this.activity, ButtonCategory)
            popupMenu.menuInflater.inflate(R.menu.popupcategory,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            popupMenu.show()
        }

        ButtonStyple.setOnClickListener {
            val popupMenu = PopupMenu(this.activity, ButtonCategory)
            popupMenu.menuInflater.inflate(R.menu.popupstyle,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            popupMenu.show()
        }

        ButtonColor.setOnClickListener {
            val popupMenu = PopupMenu(this.activity, ButtonColor)
            popupMenu.menuInflater.inflate(R.menu.popupcolor,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            popupMenu.show()
        }

        ButtonLength.setOnClickListener {
            val popupMenu = PopupMenu(this.activity, ButtonLength)
            popupMenu.menuInflater.inflate(R.menu.popuplength,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            popupMenu.show()
        }


        ButtonFit.setOnClickListener {
            val popupMenu = PopupMenu(this.activity, ButtonFit)
            popupMenu.menuInflater.inflate(R.menu.popupfit,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@NextFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            popupMenu.show()
        }
    }



}
