package com.example.myapplication.utils

class Constants {

    object Queries {
        const val GET_MOVIEDETAIL = "SELECT * FROM movieDetails WHERE imdbID = :imdbID LIMIT 1"
        const val IS_IMDBIDEXIST = "SELECT EXISTS(SELECT * FROM movieDetails WHERE imdbID = :imdbID)"
        const val DELETE_MOVIEDETAIL = "DELETE FROM movieDetails WHERE imdbID = :imdbID"
    }

    companion object{
        const val BASE_URL = "https://www.omdbapi.com/"
        const val API_KEY = "6fc87060"

    }
}