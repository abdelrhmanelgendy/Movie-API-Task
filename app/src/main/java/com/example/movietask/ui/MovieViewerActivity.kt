package com.example.movietask.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.movietask.R
import com.example.movietask.converters.MovieResultConverter
import com.example.movietask.databinding.ActivityMovieViewerBinding
import com.example.movietask.models.Result
import com.example.movietask.viewmodels.MovieViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MovieViewerActivity : AppCompatActivity() {
    companion object {
        val imageUrl = "imageUrl"
        val videoUrl = "vidoUrl"
    }

    lateinit var movieViewerBinding: ActivityMovieViewerBinding

    val movieViewModel by lazy {
        getViewModel<MovieViewModel>()
    }
    val API_Key by lazy { resources.getString(R.string.API_KEY) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieViewerBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_viewer)
        intent?.let {
            val result = it.getStringExtra(HomeActivity.MOVIE_RESUTL)
            val movieResult = MovieResultConverter().convertToResult(result!!)
            setUpData(movieResult)
        }

    }

    private fun setUpData(movieResult: Result) {
        movieViewerBinding.movieViewActivityToolBar.setTitle(movieResult.title)
        setSupportActionBar(movieViewerBinding.movieViewActivityToolBar)
        movieViewerBinding.movieViewActivityTVMovieName.setText(movieResult.title)
        movieViewerBinding.movieViewActivityTVMovieDetails.setText(movieResult.overview)
        movieViewerBinding.movieViewActivityTVMovieRate.setText("Rate: ${movieResult.vote_average.toString()}")
        movieViewerBinding.movieViewActivityTVMoviePupblishData.setText("Date: ${movieResult.release_date.toString()}")
        Picasso.get().load( movieResult.poster_path)
            .into(movieViewerBinding.movieViewActivityMainPosterImage)
        Picasso.get().load(movieResult.backdrop_path)
            .into(movieViewerBinding.movieViewActivitySecondPosterImage)

        movieViewerBinding.movieViewActivityMainPosterImage.setOnClickListener {
            ViewImage(movieResult.poster_path)
        }
        movieViewerBinding.movieViewActivitySecondPosterImage.setOnClickListener {
            ViewImage(movieResult.backdrop_path)
        }
        movieViewerBinding.movieViewActivityImgYoutubePlayer.setOnClickListener {
            openYoutubePlayer(movieResult.id)
        }
    }

    private fun openYoutubePlayer(id: Int) {
        val intent = Intent(this, MovieTrailerActivity::class.java)
        movieViewModel.findMovieTrailer(API_Key, id)
        lifecycleScope.launch(Dispatchers.Main) {
            movieViewModel.movieTrailerStateFlow.collect {
                intent.putExtra(videoUrl, it.results.get(0).key)
                startActivity(intent)
            }
        }
    }

    private fun ViewImage(posterPath: String) {
        val imageViewIntent = Intent(this, ImageViewer::class.java)
        imageViewIntent.putExtra(imageUrl, posterPath)
        startActivity(imageViewIntent)


    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}