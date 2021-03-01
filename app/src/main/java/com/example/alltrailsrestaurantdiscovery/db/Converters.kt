package com.example.alltrailsrestaurantdiscovery.db

import androidx.room.TypeConverter
import com.example.alltrailsrestaurantdiscovery.model.Geometry
import com.example.alltrailsrestaurantdiscovery.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun stringToGeometry(value: String?): Geometry? {
        return Gson().fromJson(value, Geometry::class.java)
    }

    @TypeConverter
    fun geometryToString(geometry: Geometry?): String? {
        return Gson().toJson(geometry)
    }

    @TypeConverter
    fun stringToPhoto(value: String?): Photo? {
        return Gson().fromJson(value, Photo::class.java)
    }

    @TypeConverter
    fun PhotoToString(photo: Photo?): String? {
        return Gson().toJson(photo)
    }

    @TypeConverter
    fun fromString(value: String?): List<String?>? {
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromPhotoString(value: String?): List<Photo?>? {
        val photo = Gson().fromJson(value, Photo::class.java)
        return listOf(photo)
    }

    @TypeConverter
    fun fromPhotoList(list: List<Photo?>?): String? {
        return Gson().toJson(list?.get(0))
    }
}