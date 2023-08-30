package com.example.myapplication.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetails
import com.example.myapplication.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MovieRepoImpl
@Inject
constructor(
    private val api: API
): MovieRepository{
    override fun getAllMovies(s: String, type: String): Flow<PagingData<Movie>> = Pager(
        PagingConfig(1)
    ) {
        MoviePagingCall(api, s)
    }.flow

    override fun getMovieDetails(imdbID: String): Flow<Resource<MovieDetails>> = flow {
        try {
            emit(Resource.loading(null))
            emit(Resource.success(api.getMovieDetails(imdbID).toMovieDetails()))

        } catch (e: Exception) {
            emit(Resource.error("Check Network Connection!",null))
        }
    }

}