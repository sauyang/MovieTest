package com.example.myapplication.database

import androidx.room.TypeConverter
import com.example.myapplication.repository.RatingsDTO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatingsDTOConverter {

    @TypeConverter
    fun fromRatingsDTOList(value: List<RatingsDTO>): String {
        val gson = Gson()
        val type = object : TypeToken<List<RatingsDTO>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toRatingsDTOList(value: String): List<RatingsDTO> {
        val gson = Gson()
        val type = object : TypeToken<List<RatingsDTO>>() {}.type
        return gson.fromJson(value, type)
    }
}