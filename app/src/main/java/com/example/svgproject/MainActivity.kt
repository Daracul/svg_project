package com.example.svgproject

//import coil.ImageLoader
//import coil.decode.SvgDecoder
//import coil.load

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pixplicity.sharp.Sharp
import okhttp3.*
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    companion object {
        private const val SVG_IMAGE =
            "https://dev.w3.org/SVG/tools/svgweb/samples/svg-files/android.svg"
        private const val SVG_IMAGE_CONVERTED = "https://i.ibb.co/0CpbgYq/android-3.png"
        private const val SVG_IMAGE_CARTMAN = "https://iconape.com/wp-content/files/ui/155332/svg/155332.svg"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageView: ImageView = findViewById(R.id.svg_image)
        findViewById<TextView>(R.id.text).setOnClickListener {
            loadImageGlide(imageView)
//            loadImageGlide(imageView)
//            loadImageOkHttpAndSharp(imageView)
        }



    }

    private fun loadImageOkHttpAndSharp(target: ImageView) {
        val httpClient: OkHttpClient = OkHttpClient.Builder().build()

        val request: Request = Request.Builder().url(SVG_IMAGE_CARTMAN).build()
        httpClient.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val stream: InputStream? = response.body?.byteStream()
                Sharp.loadInputStream(stream).into(target)
                stream?.close()
            }

            override fun onFailure(call: Call, e: IOException) {
                // do nothing
            }
        })
    }

    private fun loadImageGlide(imageView: ImageView) {
        GlideApp.with(this)
            .`as`(PictureDrawable::class.java)
            .load(SVG_IMAGE)
            .listener(SvgSoftwareLayerSetter())
            .into(imageView)
    }

//    private fun loadImageCoil(imageView: ImageView){
//        val imageLoader = ImageLoader.Builder(applicationContext)
//            .components {
//                add(SvgDecoder.Factory())
//            }
//            .build()
//        imageView.load(SVG_IMAGE,imageLoader)
//    }
}