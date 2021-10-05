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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_image_viewer)

        intent?.let {
            val imgUrl = it.getStringExtra(MovieViewerActivity.imageUrl)
            Picasso.get().load(imgUrl)
                .into(imageViewerBinding.imageViewActivityMainImageView)
        }
        imageViewerBinding.imageViewActivityImageVack.setOnClickListener {
            onBackPressed()
        }


    }
}