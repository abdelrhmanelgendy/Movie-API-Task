package com.example.movietask.converters

import com.google.gson.Gson
import javax.xml.transform.Result

class MovieResultConverter() {
    val gson = Gson()
    fun convertToString(result: com.example.movietask.models.Result): String {
        return gson.toJson(result)
    }

    fun convertToResult(result: String): com.example.movietask.models.Result {
        return gson.fromJson(result, com.example.movietask.models.Result::class.java)
    }


}