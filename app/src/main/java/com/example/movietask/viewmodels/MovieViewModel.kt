package com.example.movietask.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import com.example.movietask.repository.MovieRepository
import com.example.movietask.util.DataClasses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.get
import org.koin.core.qualifier.named

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val popularMovieStateFlow: MutableStateFlow<Movies> = MutableStateFlow(DataClasses.getMovie())
    val seacrMovieStateFlow: MutableStateFlow<Movies> = MutableStateFlow(DataClasses.getMovie())
    val movieTrailerStateFlow: MutableStateFlow<MovieTrailer> = MutableStateFlow(DataClasses.getMovieTrailer())
    val restOfUrl = "https://image.tmdb.org/t/p/w500/"

    fun getAllMovies(apiKey: String, pageNumber: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.getAllMovies(apiKey, pageNumber)
            withContext(Dispatchers.Main)
            {
                for(movie in allMovies.results)
                {
                    movie.poster_path=restOfUrl+movie.poster_path
                    movie.backdrop_path=restOfUrl+movie.backdrop_path
                }
                seacrMovieStateFlow.emit(allMovies)
            }

        }
    }

    fun searchMovie(apiKey: String, searchKeyWord: String,pageNumber:String) {
        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.searchForMovie(apiKey, searchKeyWord,pageNumber)
            withContext(Dispatchers.Main)
            {
                for(movie in allMovies.results)
                {
                    movie.poster_path=restOfUrl+movie.poster_path
                    movie.backdrop_path=restOfUrl+movie.backdrop_path
                }
                popularMovieStateFlow.emit(allMovies)

            }
        }
    }

    fun findMovieTrailer(apiKey: String, movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val allMovies = repository.getVideoTrailer(apiKey, movieId)
            withContext(Dispatchers.Main)
            {
                movieTrailerStateFlow.emit(allMovies)
            }
        }
    }


}