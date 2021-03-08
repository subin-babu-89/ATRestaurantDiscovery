package com.example.alltrailsatlunch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants")
data class Result(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val business_status: String,
    val geometry: Geometry,
    val icon: String,
    val name: String,
    val opening_hours: OpeningHours?,
    val permanently_closed: Boolean?,
    val photos: List<Photo?>?,
    val place_id: String,
    val plus_code: PlusCode?,
    val price_level: Int?,
    val rating: Float,
    val reference: String,
    val scope: String,
    val types: List<String>,
    val user_ratings_total: Int,
    val vicinity: String,
    val favorite: Boolean = false
)