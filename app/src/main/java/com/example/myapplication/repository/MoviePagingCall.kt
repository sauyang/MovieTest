package com.example.myapplication.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MoviePagingCall
@Inject
constructor(private val api : API, private val search : String) : PagingSource<Int, Movie>(){
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
                anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1

            val response = api.getAllMovies(search, t = "movie").data.map { it.toMovie() }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = page +1
            )
        } catch (e : IOException) {
            LoadResult.Error(e)
        } catch (e : HttpException) {
            LoadResult.Error(e)
        }
    }
}