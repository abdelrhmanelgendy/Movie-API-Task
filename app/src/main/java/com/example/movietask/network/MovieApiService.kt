package com.example.movietask.network

import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
//Base URL  https://api.themoviedb.org/3/movie/

    @GET("movie/popular")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String,

    ): Movies

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String,
    ): Movies

    //https://api.themoviedb.org/3/movie/681887/videos?api_key=e9cd58edf21c2107fc8a33ed86221ecc
    @GET("movie/{path}/videos")
    suspend fun getMovieTrailerData(
        @Path("path") id: Int,
        @Query("api_key") apiKey: String

    ): MovieTrailer

}