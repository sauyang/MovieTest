package com.example.myapplication.repository

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    @SerializedName("Search")
    val data: List<MovieDto>,

)
