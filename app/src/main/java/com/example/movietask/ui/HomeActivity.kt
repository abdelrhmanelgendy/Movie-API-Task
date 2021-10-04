package com.example.movietask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movietask.BuildConfig

import com.example.movietask.R
import com.example.movietask.adapter.MovieAdapter
import com.example.movietask.converters.MovieResultConverter
import com.example.movietask.databinding.FragementHomeBinding
import com.example.movietask.models.Result

import com.example.movietask.util.OnMovieClickListener
import com.example.movietask.viewmodels.MovieViewModel
import com.google.gson.Gson
import org.koin.androidx.viewmodel.ext.android.getViewModel


class HomeActivity : AppCompatActivity(), OnMovieClickListener {
    companion object {
        const val MOVIE_RESUTL = "movie_result"
    }

    lateinit var fragementHomeBinding: FragementHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var movieAdapter: MovieAdapter
    val movieViewModel: MovieViewModel by lazy {
        getViewModel<MovieViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragementHomeBinding = DataBindingUtil.setContentView(this, R.layout.fragement_home)
        initViews()


    }

    private fun initViews() {

        gridLayoutManager =
            GridLayoutManager(applicationContext, 2)
        movieAdapter = MovieAdapter(applicationContext, this)
        fragementHomeBinding.homeFragmentRecyclerView.adapter = movieAdapter
        fragementHomeBinding.homeFragmentRecyclerView.layoutManager = gridLayoutManager
        fragementHomeBinding.homeFragmentImgClearSearch.setOnClickListener {
            getPopularMovie()
            fragementHomeBinding.homeFragmentETSearch.setText("")
        }
        getPopularMovie()

        fragementHomeBinding.homeFragmentETSearch.setOnEditorActionListener(object :
            TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchByName(v?.text.toString())
                    return true
                }
                return false
            }

        })
    }

    private fun searchByName(keyWord: String) {

        movieViewModel.searchMovie(BuildConfig.API_KEY, keyWord)
        movieViewModel.movieSearchMutableLiveData.observe(this, {
            movieAdapter.setMovieList(it.results)
        })


    }

    override fun onClick(result: Result) {

        val movieViewerIntetn = Intent(this, MovieViewerActivity::class.java)
        val resultConverted = MovieResultConverter().convertToString(result)
        movieViewerIntetn.putExtra(MOVIE_RESUTL, resultConverted)
        startActivity(movieViewerIntetn)

    }
fun getPopularMovie()
    {
        movieViewModel.getAllMovies(BuildConfig.API_KEY)
        movieViewModel.allMoviesMutableLiveData.observe(this,
            {
                movieAdapter.setMovieList(it.results)

            })
    }

}
