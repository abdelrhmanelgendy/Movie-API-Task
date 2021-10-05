package com.example.movietask.util

import com.example.movietask.models.MovieTrailer
import com.example.movietask.models.Movies
import com.example.movietask.models.Result
import com.example.movietask.models.ResultX
import org.koin.core.context.GlobalContext.get

object DataClasses {
    fun getMovie(): Movies {
        return Movies(-1, listOf(getMovieResult()), -1, -1)
    }

    private fun getMovieResult(): Result {
        return Result(
            true,
            "",
            listOf(1),
            1,
            "en",
            "home alone",
            "1",
            9.9,
            "-1",
            "2021",
            "-1",
            false,
            9.9,
            9
        )
    }

    private fun getMovieTrailerResult(): ResultX {
        return ResultX("-1", "-1", "-1", "-1", "-1", false, "-1", "-1", -1, "-1")
    }

    fun getMovieTrailer(): MovieTrailer {
        return MovieTrailer(1, listOf(getMovieTrailerResult()))

    }
}