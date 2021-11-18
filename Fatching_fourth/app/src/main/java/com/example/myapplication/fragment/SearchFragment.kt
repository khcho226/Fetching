package com.example.myapplication.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_search.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Package.getPackage
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_search, container, false)
        view.findViewById<Button>(R.id.Searchbutton_search).setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.action_searchFragment_to_blankFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var userimage: ImageView
        button222.setOnClickListener{ ///////////////// 이걸 누르면 사진이 서버로 전송됨.
            for(i in 0..4){
                val bitmap = BitmapFactory.decodeResource(resources, R.drawable.mannish_33); // picture to send!!!!

                // 여기에 파일 경로를 넣으면됩니다!!!!!
                val file = File("/sdcard/Android/data/com.example.serverpractice/files/pictures", "img.jpg")

                val outputStream = FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
                outputStream.close()

                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart(
                        "image", "img.jpg",
                        RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                    )
                    .addFormDataPart("somParam", "someValue")
                    .build()

                if (file.exists()) {
                    println("i am here")
                } else {
                    println("not!!!!")
                }

                val url = "http://192.168.219.100:5000/"
                val client = OkHttpClient.Builder()
                    .connectTimeout(1000, TimeUnit.SECONDS)
                    .writeTimeout(1000, TimeUnit.SECONDS)
                    .readTimeout(1000, TimeUnit.SECONDS)
                    .build();


                val request = Request.Builder().url(url).header("Connection", "close").post(requestBody).build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        e.printStackTrace()
                        println("error!!!")
                    }

                    override fun onResponse(call: Call, response: Response) {
                        // val textView = findViewById<TextView>(R.id.textView)

                        val file = response.body?.byteStream()
                        val bitmap = BitmapFactory.decodeStream(file)

                        val img = "imageView2"
                        var a = i+2

                        val resId = context?.resources?.getIdentifier("imageView$a","id",
                            context?.packageName
                        )


                        activity?.runOnUiThread {
                            userimage = view.findViewById(resId!!)
                            userimage.setImageBitmap(bitmap);
                        }


                    }
                })
            }


        }
    }


}