package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class Photo {
    @Json(name = "height")
    var height: Int? = null

    @Json(name = "photo_reference")
    var photoReference: String? = null

    @Json(name = "width")
    var width: Int? = null
}