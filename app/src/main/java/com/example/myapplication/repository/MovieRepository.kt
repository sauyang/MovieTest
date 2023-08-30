package com.example.myapplication.repository


import androidx.paging.PagingData
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetails
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow


interface MovieRepository {

    fun getAllMovies(s : String, type : String) : Flow<PagingData<Movie>>

    fun getMovieDetails(movieID : String) :Flow<Resource<MovieDetails>>
}