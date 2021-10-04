package com.example.movietask.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import com.example.movietask.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val movieSearchMutableLiveData: MutableLiveData<Movies> = MutableLiveData()
    val allMoviesMutableLiveData: MutableLiveData<Movies> = MutableLiveData()
    val movieTrailer: MutableLiveData<MovieTrailer> = MutableLiveData()

    fun getAllMovies(apiKey: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.getAllMovies(apiKey)
            withContext(Dispatchers.Main)
            {
                allMoviesMutableLiveData.value = allMovies
            }

        }
    }

    fun searchMovie(apiKey: String, searchKeyWord: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.searchForMovie(apiKey, searchKeyWord)
            withContext(Dispatchers.Main)
            {
                movieSearchMutableLiveData.value = allMovies
            }
        }
    }
    fun findMovieTrailer(apiKey: String,movieId:Int)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.getVideoTrailer(apiKey, movieId)
            withContext(Dispatchers.Main)
            {
                movieTrailer.value = allMovies
            }
        }
    }


}