package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class Geometry {
    @Json(name = "location")
    var location: Location? = null

    @Json(name = "viewport")
    var viewport: Viewport? = null
}