package com.example.myapplication.repository

import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET(".")
    suspend fun getAllMovies(
        @Query("s") s : String? = "Marvel",
        @Query("type") t : String? = "movie"
    ) : MovieListResponse

    @GET(".")
    suspend fun getMovieDetails(@Query("i") i : String? = null) : MovieDto
}