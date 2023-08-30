package com.example.myapplication.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.MovieDetails
import com.example.myapplication.utils.Constants

@Dao
interface MovieDetailsDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetails(movie : MovieDetails)

    @Query(Constants.Queries.IS_IMDBIDEXIST)
    suspend fun isIMDBIDExists(imdbID : String): Boolean

    @Query(Constants.Queries.GET_MOVIEDETAIL)
    suspend fun fetchMovieDetails(imdbID : String): MovieDetails

    @Query(Constants.Queries.DELETE_MOVIEDETAIL)
    suspend fun removeMovieDetails(imdbID : String)

}