package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class OpeningHours {
    @Json(name = "open_now")
    var openNow: Boolean? = null
}