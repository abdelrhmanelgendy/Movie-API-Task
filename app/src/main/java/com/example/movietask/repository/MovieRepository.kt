package com.example.movietask.repository

import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import com.example.movietask.network.MovieApiService

class MovieRepository(var movieApiService: MovieApiService) {
    suspend fun getAllMovies(apiKey: String,pageNumber:String): Movies {
        return movieApiService.getAllMovies(apiKey,pageNumber)
    }

    suspend fun searchForMovie(apiKey: String, searchQuery: String,pageNumber:String): Movies {
        return movieApiService.searchMovie(apiKey, searchQuery,pageNumber)
    }
    suspend fun getVideoTrailer(apiKey: String,movieId:Int):MovieTrailer
    {
        return movieApiService.getMovieTrailerData(movieId,apiKey)
    }
}