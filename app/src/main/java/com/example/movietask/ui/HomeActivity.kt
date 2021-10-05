package com.example.movietask.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager

import com.example.movietask.R
import com.example.movietask.adapter.MovieAdapter
import com.example.movietask.converters.MovieResultConverter
import com.example.movietask.databinding.FragementHomeBinding
import com.example.movietask.models.Result
import com.example.movietask.util.DataClasses

import com.example.movietask.util.OnMovieClickListener
import com.example.movietask.viewmodels.MovieViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel


class HomeActivity : AppCompatActivity(), OnMovieClickListener {
    companion object {
        const val MOVIE_RESUTL = "movie_result"
    }

    lateinit var fragementHomeBinding: FragementHomeBinding
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var movieAdapter: MovieAdapter
    val API_Key by lazy { resources.getString(R.string.API_KEY) }
    val movieViewModel: MovieViewModel by lazy {
        getViewModel()
    }
    var pageNumber= 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragementHomeBinding = DataBindingUtil.setContentView(this, R.layout.fragement_home)
        initViews()


    }

    private fun initViews() {

        fragementHomeBinding.homeFragmentRecyclerView.also {
            it.setHasFixedSize(true)
            it.isNestedScrollingEnabled = false;
        }
        gridLayoutManager =
            GridLayoutManager(applicationContext, 2)
        movieAdapter = MovieAdapter(applicationContext, this)
        movieAdapter.setHasStableIds(true)
        fragementHomeBinding.homeFragmentRecyclerView.adapter = movieAdapter
        fragementHomeBinding.homeFragmentRecyclerView.layoutManager = gridLayoutManager
        fragementHomeBinding.homeFragmentImgClearSearch.setOnClickListener {
            movieAdapter.movieList.clear()
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
        createRecyclerViewPagination()
    }

    private fun createRecyclerViewPagination() {
        fragementHomeBinding.homeFragmentNastedScrollView.setOnScrollChangeListener(object :
            NestedScrollView.OnScrollChangeListener {
            override fun onScrollChange(
                v: NestedScrollView?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if (scrollY == v!!.getChildAt(0).measuredHeight - v.measuredHeight) {
                    getMoreData()
                }
            }

        })

    }

    private fun getMoreData() {

        pageNumber++
        movieViewModel.getAllMovies(API_Key, pageNumber.toString())



    }

    private fun searchByName(keyWord: String) {

        movieViewModel.searchMovie(API_Key, keyWord,"1")
        lifecycleScope.launch(Dispatchers.Main) {
            movieViewModel.popularMovieStateFlow.collect {
                if (it!=DataClasses.getMovie())
                {
                    movieAdapter.setMovieListAndDeleteOld(it.results)
                }
            }
        }


    }

    override fun onClick(result: Result) {

        val movieViewerIntetn = Intent(this, MovieViewerActivity::class.java)
        val resultConverted = MovieResultConverter().convertToString(result)
        movieViewerIntetn.putExtra(MOVIE_RESUTL, resultConverted)
        startActivity(movieViewerIntetn)

    }

    fun getPopularMovie() {
        movieViewModel.getAllMovies(API_Key, "1")
        lifecycleScope.launch(Dispatchers.Main) {
            movieViewModel.seacrMovieStateFlow.collect() {
                if (it != DataClasses.getMovie()) {
                    movieAdapter.setMovieList(it.results)
                }

            }
        }


    }


}
