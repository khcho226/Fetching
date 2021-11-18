package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_blank.*
import kotlinx.android.synthetic.main.fragment_next.*
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.*
import java.util.*

class NextFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_next, container, false)
        view.findViewById<Button>(R.id.Searchbutton).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_nextFragment_to_searchFragment)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        uploadPictureCamera.setOnClickListener {

            if ( (activity as MainActivity).checkPermission() ){
                (activity as MainActivity).dispatchTakePictureIntent()
            }
            else {
                (activity as MainActivity).requestPermission()
            }
        }

        uploadPictureGallery.setOnClickListener {

            if ( (activity as MainActivity).checkPermission() ){
                (activity as MainActivity).openGalleryForImage()
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


