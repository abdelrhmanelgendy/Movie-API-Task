package com.example.movietask.network

import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getAllMovies(
        @Query("api_key") apiKey: String,
        @Query("page") pageNumber: String,

    ): Movies

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") searchQuery: String,
    ): Movies

    @GET("movie/{path}/videos")
    suspend fun getMovieTrailerData(
        @Path("path") id: Int,
        @Query("api_key") apiKey: String

    ): MovieTrailer

}