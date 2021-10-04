package com.example.movietask.di

import com.example.movietask.network.MovieApiService
import com.example.movietask.repository.MovieRepository
import com.example.movietask.viewmodels.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApiService::class.java)
    }

    factory {
        MovieRepository(get())
    }

    viewModel {
        MovieViewModel(get())
    }
}