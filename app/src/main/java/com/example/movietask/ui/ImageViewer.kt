package com.example.movietask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.movietask.R
import com.example.movietask.databinding.ActivityImageViewerBinding
import com.squareup.picasso.Picasso

class ImageViewer : AppCompatActivity() {
    lateinit var imageViewerBinding: ActivityImageViewerBinding
    val restOfUrl = "https://image.tmdb.org/t/p/w500/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer)

        intent?.let {
            val imgUrl = it.getStringExtra(MovieViewerActivity.imageUrl)
            Picasso.get().load(restOfUrl+imgUrl)
                .into(imageViewerBinding.imageViewActivityMainImageView)
        }
        imageViewerBinding.imageViewActivityImageVack.setOnClickListener {
            onBackPressed()
        }


    }
}