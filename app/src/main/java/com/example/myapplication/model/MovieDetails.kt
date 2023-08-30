package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.repository.RatingsDTO

@Entity(tableName = "movieDetails")
data class MovieDetails (

    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    val Title: String,
    val Year: String,
    val Rated: String,
    val Released: String,
    val Runtime: String,
    val Genre: String,
    val Director: String,
    val Actors: String,
    val Plot: String,
    val Language: String,
    val Country: String,
    val Awards: String,
    val Poster: String,
    val Ratings: List<RatingsDTO>,
    val Metascore: String,
    val imdbRating: String,
    val imdbVotes: String,
    val Type: String,
    val DVD: String,
    val BoxOffice: String,
    val Production: String,
    val Website: String,
    val Response: String,

)

data class Ratings(
    val Source: String,
    val Value: String,
)

