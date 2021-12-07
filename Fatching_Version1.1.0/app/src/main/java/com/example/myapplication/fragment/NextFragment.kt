package com.example.myapplication.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
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
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_next.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class NextFragment : Fragment() {

    var mImageView: ImageView? = null
    val REQUEST_IMAGE_CAPTURE=1
    val REQUEST_GALLERY_TAKE = 2
    var pathfromblank = "null"
    lateinit var currentPhotoPath: String
    lateinit var imgpath: Uri


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

//        view.findViewById<Button>(R.id.Searchbutton).setOnClickListener{
//
//            val path = imgpath.toString()
//            val im = Uri.parse(path)
//            val a = im.path // 카메라에서 온 경우
//            val action = NextFragmentDirections.actionNextFragmentToSearchFragment(path = a)
//            findNavController().navigate(action)
////
////            //Navigation.findNavController(view).navigate(R.id.action_nextFragment_to_searchFragment)
//        }

        ///////////////////////////////////////////////////////
        val style_spinner = view.findViewById<Spinner>(R.id.Style_spinner)
        var styleData = resources.getStringArray(R.array.styles)
        style_spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, styleData) as SpinnerAdapter

        style_spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        val category_spinner = view.findViewById<Spinner>(R.id.Category_spinner)
        var categoryData = resources.getStringArray(R.array.category)
        category_spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, categoryData) as SpinnerAdapter

        category_spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        val color_spinner = view.findViewById<Spinner>(R.id.Color_spinner)
        var colorData = resources.getStringArray(R.array.color)
        color_spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, colorData) as SpinnerAdapter

        color_spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        val fit_spinner = view.findViewById<Spinner>(R.id.Fit_spinner)
        var fitData = resources.getStringArray(R.array.fit)
        fit_spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, fitData) as SpinnerAdapter

        fit_spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        val length_spinner = view.findViewById<Spinner>(R.id.Length_spinner)
        var lengthData = resources.getStringArray(R.array.length)
        length_spinner?.adapter = ArrayAdapter(activity?.applicationContext!!, R.layout.spinner_item, lengthData) as SpinnerAdapter

        length_spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val type = parent?.getItemAtPosition(position).toString()
                // Toast.makeText(activity,type, Toast.LENGTH_LONG).show()
            }

        }
        ///////////////////////////////////////////////////////


        return view
    }

//    var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

//    private fun requestPermission() {
//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
//            Manifest.permission.READ_EXTERNAL_STORAGE,
//            Manifest.permission.CAMERA
//        ), REQUEST_IMAGE_CAPTURE)
//    }
//
//    private fun checkPermission(): Boolean {
//        return (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)==
//                PackageManager.PERMISSION_GRANTED&& ContextCompat.checkSelfPermission(requireContext(),
//            Manifest.permission.READ_EXTERNAL_STORAGE
//        )== PackageManager.PERMISSION_GRANTED)
//    }

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

//    private fun dispatchTakePictureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            if (takePictureIntent.resolveActivity(this.packageManager!!) != null) {
//                val photoFile: File? =
//                    try {
//                        createImageFile()
//                    } catch (ex: IOException) {
//                        Log.d("TAG", "error occurred during creating image file")
//                        null
//                    }
//                if (Build.VERSION.SDK_INT < 24) {
//                    if (photoFile != null) {
//                        val photoURI = Uri.fromFile(photoFile)
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//                    }
//                } else {
//                    photoFile?.also {
//                        val photoURI: Uri = FileProvider.getUriForFile(
//                            requireContext(), "com.example.myapplication", it
//                        )
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
//                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//                    }
//                }
//            }
//        }
//    }


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
                        pathfromblank=Uri.fromFile(file).toString()
                        Log.d("URI!!!", Uri.fromFile(file).toString())
                    }
                    else{
                        val decode = ImageDecoder.createSource(
                            activity?.contentResolver!!,
                            Uri.fromFile(file))
                        val bitmap = ImageDecoder.decodeBitmap(decode)
                        mImageView?.setImageBitmap(bitmap)
                        pathfromblank=Uri.fromFile(file).toString()
                        Log.d("URI!!!", Uri.fromFile(file).toString())
                    }
                }
            }

            2-> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    val returnUri = data!!.data
                    val bitmapImage =
                        MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, returnUri)
                    mImageView?.setImageBitmap(bitmapImage)
                    mImageView?.bringToFront()

                    pathfromblank = returnUri.toString()


                    Log.d("URI!!!", returnUri.toString())
                    }
                }

//            2 -> {
//
//                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
//                    mImageView?.setImageURI(data?.data)  // handle chosen image
//                   mImageView?.bringToFront()
//
//
//                    val bundle = Bundle()
//                    bundle.putParcelable("ImageURI",data?.data);
//                    val fragobj = SearchFragment()
//                    fragobj.setArguments(bundle)
//
//                }
//            }
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
        Log.d("TAG", "hello world")
        uploadPictureCamera.setOnClickListener { // 사진 카메라로 부터 받아오기. (클릭시)
            if ( (activity as MainActivity).checkPermission() ){
                dispatchTakePictureIntent()

                Log.d("TAG", imgpath.toString())
                Glide.with(this)
                    .load(imgpath)
                    .into(imageView7)

                imageView15.visibility = View.INVISIBLE
                textViewIfYou.visibility= View.INVISIBLE
            }
            else {
                (activity as MainActivity).requestPermission()
            }
        }

        uploadPictureGallery.setOnClickListener {
            if ( (activity as MainActivity).checkPermission() ){
                startGallery()
                imageView15.visibility = View.INVISIBLE
                textViewIfYou.visibility= View.INVISIBLE


            }
            else {
                (activity as MainActivity).requestPermission()

            }
        }

        Searchbutton.setOnClickListener{
            println(pathfromblank)
            val im = Uri.parse(pathfromblank)
            var path = getRealPathFromURI(im)
            val action = NextFragmentDirections.actionNextFragmentToSearchFragment(path = path)
            findNavController().navigate(action)
            // navController.navigate(R.id.action_blankFragment_to_searchFragment)
        }

//        Searchbutton.setOnClickListener{
////            println(pathfromblank)
////            val im = Uri.parse(pathfromblank)
////            var path = getRealPathFromURI(im)
////            val action = NextFragmentDirections.actionNextFragmentToSearchFragment(path = path)
////            findNavController().navigate(action)
//             navController.navigate(R.id.action_blankFragment_to_searchFragment)
//        }

//        ButtonCategory.setOnClickListener {
//            val popupMenu = PopupMenu(this.activity, ButtonCategory)
//            popupMenu.menuInflater.inflate(R.menu.popupcategory,popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
//            popupMenu.show()
//        }
//
//        ButtonStyple.setOnClickListener {
//            val popupMenu = PopupMenu(this.activity, ButtonStyple)
//            popupMenu.menuInflater.inflate(R.menu.popupstyle,popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
//            popupMenu.show()
//        }
//
//        ButtonColor.setOnClickListener {
//            val popupMenu = PopupMenu(this.activity, ButtonLength)
//            popupMenu.menuInflater.inflate(R.menu.popupcolor,popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
//            popupMenu.show()
//        }
//
//        ButtonLength.setOnClickListener {
//            val popupMenu = PopupMenu(this.activity, ButtonLength)
//            popupMenu.menuInflater.inflate(R.menu.popuplength,popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//                true
//            }
//            popupMenu.show()
//        }
//
//
//        ButtonFit.setOnClickListener {
//            val popupMenu = PopupMenu(this.activity, ButtonFit)
//            popupMenu.menuInflater.inflate(R.menu.popupfit,popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { item ->
//                when (item.itemId) {
//                    R.id.category_top -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_bottom -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_dress -> Toast.makeText(
//                        this@NextFragment.activity,
//                        "You Clicked:" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    R.id.category_outer -> Toast.makeText(
//                        this@NextFragment.activity,
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



}
