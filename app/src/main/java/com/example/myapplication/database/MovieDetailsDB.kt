package com.example.myapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.myapplication.model.MovieDetails


@Database(
    entities = [MovieDetails::class], version = 1, exportSchema = false
)
@TypeConverters(RatingsDTOConverter::class)

abstract class MovieDetailsDB : RoomDatabase(){

    abstract fun movieDetailsDAO() : MovieDetailsDao
}