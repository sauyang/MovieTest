package com.example.myapplication.repository


import com.example.myapplication.model.Movie
import com.example.myapplication.model.MovieDetails
import com.example.myapplication.model.Ratings
import com.google.gson.annotations.SerializedName

class MovieDto (

    @SerializedName("Title")
    val Title: String,

    @SerializedName("Year")
    val Year: String,

    @SerializedName("imdbID")
    val imdbID: String,

    @SerializedName("Type")
    val Type: String,

    @SerializedName("Poster")
    val Poster: String,

    @SerializedName("Rated")
    val Rated: String,

    @SerializedName("Released")
    val Released: String,

    @SerializedName("Runtime")
    val Runtime: String,

    @SerializedName("Genre")
    val Genre: String,

    @SerializedName("Director")
    val Director: String,

    @SerializedName("Actors")
    val Actors: String,

    @SerializedName("Plot")
    val Plot: String,

    @SerializedName("Language")
    val Language: String,

    @SerializedName("Country")
    val Country: String,

    @SerializedName("Awards")
    val Awards: String,

    @SerializedName("Ratings")
    val Ratings: List<RatingsDTO>,

    @SerializedName("Metascore")
    val Metascore: String,

    @SerializedName("imdbRating")
    val imdbRating: String,

    @SerializedName("imdbVotes")
    val imdbVotes: String,

    @SerializedName("DVD")
    val DVD: String,

    @SerializedName("BoxOffice")
    val BoxOffice: String,

    @SerializedName("Production")
    val Production: String,

    @SerializedName("Website")
    val Website: String,

    @SerializedName("Response")
    val Response: String
    )

fun MovieDto.toMovie(): Movie {
    return Movie(
        Title = Title,
        Year = Year,
        imdbID = imdbID,
        Type = Type,
        Poster = Poster
    )
}

fun MovieDto.toMovieDetails() : MovieDetails{
    return MovieDetails(
        Title = Title,
        Year = Year,
        Rated = Rated,
        Released = Released,
        Runtime = Runtime,
        Genre = Genre,
        Director = Director,
        Actors = Actors,
        Plot = Plot,
        Language = Language,
        Country = Country,
        Awards = Awards,
        Poster = Poster,
        Ratings = Ratings,
        Metascore = Metascore,
        imdbRating = imdbRating,
        imdbVotes = imdbVotes,
        imdbID = imdbID,
        Type =  Type,
        DVD = DVD,
        BoxOffice = BoxOffice,
        Production = Production,
        Website = Website,
        Response = Response
    )
}

class RatingsDTO(
    @SerializedName("Source")
    val Source: String,

    @SerializedName("Value")
    val Value: String,
)