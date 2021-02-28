package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class Location {
    @Json(name = "lat")
    var lat: Double? = null

    @Json(name = "lng")
    var lng: Double? = null
}