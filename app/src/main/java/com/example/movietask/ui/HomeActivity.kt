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
import com.example.movietask.util.PaginationScrollListener
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
    val API_Key by lazy { resources.getString(R.string.API_KEY) }
    val movieViewModel: MovieViewModel by lazy {
        getViewModel<MovieViewModel>()
    }
    var firstPageNumber =1

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
        var isLastPage: Boolean = false
        var isLoading: Boolean = false

        fragementHomeBinding.homeFragmentRecyclerView.addOnScrollListener(object :
            PaginationScrollListener(gridLayoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                Log.d("TAG", "loadMoreItems: ")
                firstPageNumber++
                getPopularMovieByPage(firstPageNumber.toString())
                //you have to call loadmore items to get more data
//                getMoreItems()
            }
        })
    }

    private fun searchByName(keyWord: String) {

        movieViewModel.searchMovie(API_Key, keyWord)
        movieViewModel.movieSearchMutableLiveData.observe(this, {
            movieAdapter.movieList=it.results
            movieAdapter.notifyDataSetChanged()
        })


    }

    override fun onClick(result: Result) {

        val movieViewerIntetn = Intent(this, MovieViewerActivity::class.java)
        val resultConverted = MovieResultConverter().convertToString(result)
        movieViewerIntetn.putExtra(MOVIE_RESUTL, resultConverted)
        startActivity(movieViewerIntetn)

    }

    fun getPopularMovie() {
        movieViewModel.getAllMovies(API_Key,"1")
        movieViewModel.allMoviesMutableLiveData.observe(this,
            {
                movieAdapter.movieList=it.results
                movieAdapter.notifyDataSetChanged()

            })
    }
    fun getPopularMovieByPage(pageNumber:String) {
        movieViewModel.getAllMovies(API_Key,pageNumber)
        movieViewModel.allMoviesMutableLiveData.observe(this,
            {
               addData(it.results)

            })
    }


    fun addData(listItems: List<Result>) {
        movieAdapter.movieList.toMutableList().addAll(listItems)
        movieAdapter.notifyDataSetChanged()

    }

}
