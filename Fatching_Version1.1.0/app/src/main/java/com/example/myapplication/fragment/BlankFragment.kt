package com.example.myapplication.fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_blank.*
//import kotlinx.android.synthetic.main.fragment_blank.ButtonStyle_blank
import kotlinx.android.synthetic.main.fragment_next.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.text.SimpleDateFormat


class BlankFragment : Fragment() {


    val REQUEST_IMAGE_CAPTURE=1
    val REQUEST_GALLERY_TAKE =2
    var mImageview: ImageView? = null
    var pathfromblank = "null"

    lateinit var currentPhotoPath: String
    lateinit var imgpath: Uri

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        mImageview = view.findViewById(R.id.uploadedpicture)
        //view.findViewById<Button>(R.id.Searchbutton_blank).setOnClickListener{
        //    Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_searchFragment)
        //}


        ///////////////////////////////////////////////////////
        val spinner = view.findViewById<Spinner>(R.id.Style_spinner)
        var styleData = resources.getStringArray(R.array.styles)
        spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, styleData) as SpinnerAdapter

        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        ///////////////////////////////////////////////////////


        //////////////////////////////////////////////////

        /////////////////////////////////////////////
        return view
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
                        hang.visibility = View.INVISIBLE
                        camera.visibility =View.INVISIBLE
                        val bitmap = MediaStore.Images.Media
                            .getBitmap(activity?.contentResolver, Uri.fromFile(file))  //Deprecated
                        mImageview?.setImageBitmap(bitmap)
                        pathfromblank=Uri.fromFile(file).toString()
                        mImageview?.bringToFront()
                        Log.d("URI!!!", Uri.fromFile(file).toString())
                    }
                    else{
                        val decode = ImageDecoder.createSource(
                            activity?.contentResolver!!,
                            Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        mImageview?.setImageBitmap(bitmap)
                        pathfromblank=Uri.fromFile(file).toString()
                        mImageview?.bringToFront()

                        hang.visibility = View.INVISIBLE
                        camera.visibility =View.INVISIBLE
                        Log.d("URI!!!", Uri.fromFile(file).toString())
                    }
                }
            }

            2 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE) {
                        val returnUri = data!!.data
                        val bitmapImage =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, returnUri)
                        mImageview?.setImageBitmap(bitmapImage)
                        mImageview?.bringToFront()

                        pathfromblank = returnUri.toString()
                        hang.visibility = View.INVISIBLE
                        camera.visibility =View.INVISIBLE

                        Log.d("URI!!!", returnUri.toString())
                }
            }
        }
    }


    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, 2)

        }
    }

    private fun dispatchTakePictureIntent() {
        lateinit var photoURI: Uri
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also{ takePictureIntent ->
            if (takePictureIntent.resolveActivity(activity?.packageManager!!)!=null ) {
                val photoFile: File? =
                    try {
                        createImageFile()
                    } catch (ex: IOException) {
                        Log.d("TAG", "error occurred during creating image file")
                        null
                    }
                if (Build.VERSION.SDK_INT<24) {
                    if (photoFile!=null){
                        photoURI=Uri.fromFile(photoFile)
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI)
                        startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
                        Log.d("TAG", photoURI.toString())
                        imgpath=photoURI
                    }
                } else {
                    photoFile?.also {
                        photoURI= FileProvider.getUriForFile (
                            requireContext(), "com.example.myapplication", it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                        Log.d("TAG", photoURI.toString())
                        imgpath=photoURI

                    }
                }

            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera.setOnClickListener { // 사진 카메라로 부터 받아오기. (클릭시)
            if ( (activity as MainActivity).checkPermission() ){
                dispatchTakePictureIntent()

                Log.d("TAG", imgpath.toString())
                Glide.with(this)
                    .load(imgpath)
                    .into(uploadedpicture)

            }
            else {
                (activity as MainActivity).requestPermission()
            }
        }

        view.findViewById<TextView>(R.id.gallery).setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    2000
                )
            } else {
                startGallery()
            }
        }

        navController = Navigation.findNavController(view)
        detail.setOnClickListener {
            navController.navigate(R.id.action_blankFragment_to_nextFragment)
        }


        Searchbutton_blank.setOnClickListener{
            println(pathfromblank)
            val im = Uri.parse(pathfromblank)
            var path = getRealPathFromURI(im)
            val action = BlankFragmentDirections.actionBlankFragmentToSearchFragment(path = path)
            findNavController().navigate(action)
            // navController.navigate(R.id.action_blankFragment_to_searchFragment)
        }




//        ButtonStyle_blank.setOnClickListener{
//            val popupMenu = PopupMenu(this.activity, ButtonStyle_blank)
//            popupMenu.menuInflater.inflate(R.menu.popupstyle, popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@BlankFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@BlankFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@BlankFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@BlankFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
//            popupMenu.show()
//        }
    }



    private fun getRealPathFromURI(contentUri: Uri): String? {
        if (contentUri.path!!.startsWith("/storage")) {
            return contentUri.path
        }
        val id = DocumentsContract.getDocumentId(contentUri).split(":").toTypedArray()[1]
        val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
        val selection = MediaStore.Files.FileColumns._ID + " = " + id
        val cursor: Cursor? = activity?.contentResolver?.query(
            MediaStore.Files.getContentUri("external"),
            columns,
            selection,
            null,
            null
        )
        try {
            val columnIndex = cursor?.getColumnIndex(columns[0])
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    return columnIndex?.let { cursor.getString(it) }
                }
            }
        } finally {
            cursor?.close()
        }
        return null
    }
///////////////////////////////////////////////


}