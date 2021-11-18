package com.example.myapplication.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_blank.ButtonStyple_blank
import kotlinx.android.synthetic.main.fragment_next.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull



class BlankFragment : Fragment() {

    private lateinit var navController: NavController
    var mImageview: ImageView? = null
    private lateinit var imageView: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blank, container, false)

        //view.findViewById<Button>(R.id.Searchbutton_blank).setOnClickListener{
        //    Navigation.findNavController(view).navigate(R.id.action_blankFragment_to_searchFragment)
        //}


        mImageview = view.findViewById<View>(R.id.uploadedpicture) as ImageView
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
        return view
    }
    var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

    private fun startGallery() {
        val cameraIntent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        cameraIntent.type = "image/*"
        if (cameraIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(cameraIntent, 1000)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1000) {
                val returnUri = data!!.data
                val bitmapImage =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, returnUri)
                mImageview!!.setImageBitmap(bitmapImage)
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        detail.setOnClickListener {
            navController.navigate(R.id.action_blankFragment_to_nextFragment)
        }



        Searchbutton_blank.setOnClickListener{
            navController.navigate(R.id.action_blankFragment_to_searchFragment)
        }



        ButtonStyple_blank.setOnClickListener{
            val popupMenu = PopupMenu(this.activity, ButtonCategory)
            popupMenu.menuInflater.inflate(R.menu.popupstyle, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.category_top -> Toast.makeText(
                        this@BlankFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_bottom -> Toast.makeText(
                        this@BlankFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_dress -> Toast.makeText(
                        this@BlankFragment.activity,
                        "You Clicked:" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    R.id.category_outer -> Toast.makeText(
                        this@BlankFragment.activity,
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