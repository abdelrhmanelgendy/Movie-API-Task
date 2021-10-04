package com.example.movietask.util

import com.example.movietask.models.Result

interface OnMovieClickListener {
    fun onClick(result: Result)
}