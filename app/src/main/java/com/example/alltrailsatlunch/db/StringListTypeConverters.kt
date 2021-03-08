package com.example.alltrailsatlunch.db

import androidx.room.TypeConverter
import com.example.alltrailsatlunch.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

/**
 * TypeConverters needed to store class objects in a room database
 */
class StringListTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<String?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<String?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToPlusCode(data: String?): PlusCode? {
        return gson.fromJson(data, PlusCode::class.java)
    }

    @TypeConverter
    fun plusCodeToString(plusCode: PlusCode?): String? {
        return gson.toJson(plusCode)
    }

    @TypeConverter
    fun stringToOpeningHours(data: String?): OpeningHours? {
        return gson.fromJson(data, OpeningHours::class.java)
    }

    @TypeConverter
    fun openingHoursToString(openingHours: OpeningHours?): String? {
        return gson.toJson(openingHours)
    }

    @TypeConverter
    fun stringToPhotoList(data: String?): List<Photo?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Photo?>?>() {}.type
        return gson.fromJson<List<Photo?>>(data, listType)
    }

    @TypeConverter
    fun photoListToString(someObjects: List<Photo?>?): String? {
        return gson.toJson(someObjects)
    }

    @TypeConverter
    fun stringToLocation(data: String?): Location? {
        return gson.fromJson(data, Location::class.java)
    }

    @TypeConverter
    fun locationToString(location: Location?): String? {
        return gson.toJson(location)
    }

    @TypeConverter
    fun stringToGeometry(data: String?): Geometry? {
        return gson.fromJson(data, Geometry::class.java)
    }

    @TypeConverter
    fun geometryToString(geometry: Geometry?): String? {
        return gson.toJson(geometry)
    }
}