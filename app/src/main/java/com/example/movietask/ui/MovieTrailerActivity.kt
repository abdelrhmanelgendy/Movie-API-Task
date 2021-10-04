package com.example.movietask.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.movietask.R
import com.example.movietask.databinding.ActivityMovieTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView


class MovieTrailerActivity : AppCompatActivity() {
    lateinit var movieTrailerBinding: ActivityMovieTrailerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieTrailerBinding=DataBindingUtil.setContentView(this, R.layout.activity_movie_trailer)
        intent?.let {
            setUpVideoView(intent.getStringExtra(MovieViewerActivity.videoUrl))
        }
    }

    private fun setUpVideoView(videoId: String?) {
        val youTubePlayerView = movieTrailerBinding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {

                youTubePlayer.loadVideo(videoId!!, 0f)
            }
        })

    }
}