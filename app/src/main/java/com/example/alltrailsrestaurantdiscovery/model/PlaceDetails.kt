package com.example.alltrailsrestaurantdiscovery.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.squareup.moshi.Json

@Entity(tableName = "place_details")
class PlaceDetails(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @Json(name = "business_status") var businessStatus: String? = null,
    @Json(name = "formatted_address") var formattedAddress: String? = null,
    @Json(name = "formatted_phone_number") var formattedPhoneNumber: String? = null,
    @Json(name = "icon") var icon: String? = null,
    @Json(name = "international_phone_number") var internationalPhoneNumber: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "permanently_closed") var permanentlyClosed: Boolean? = null,
    @Json(name = "place_id") var placeId: String? = null,
    @Json(name = "price_level") var priceLevel: Int? = null,
    @Json(name = "rating") var rating: Double? = null,
    @Json(name = "reference") var reference: String? = null,
    @Json(name = "url") var url: String? = null,
    @Json(name = "user_ratings_total") var userRatingsTotal: Int? = null,
    @Json(name = "utc_offset") var utcOffset: Int? = null,
    @Json(name = "vicinity") var vicinity: String? = null,
    @Json(name = "website") var website: String? = null
) {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}