package com.example.alltrailsrestaurantdiscovery.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "restaurant")
data class Result(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @Json(name = "business_status") var businessStatus: String? = null,
    @Json(name = "formatted_address") var formattedAddress: String? = null,
    @Json(name = "icon") var icon: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "place_id") var placeId: String? = null,
    @Json(name = "price_level") var priceLevel: Int? = null,
    @Json(name = "rating") var rating: Double? = null,
    @Json(name = "reference") var reference: String? = null,
    @Json(name = "user_ratings_total") var userRatingsTotal: Int? = null,
    @Json(name = "permanently_closed") var permanentlyClosed: Boolean? = null,
    @Transient @ColumnInfo(name = "favorite")var isFavorite : Boolean = false
)