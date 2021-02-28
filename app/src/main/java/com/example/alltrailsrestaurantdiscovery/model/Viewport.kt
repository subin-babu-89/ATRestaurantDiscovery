package com.example.alltrailsrestaurantdiscovery.model

import com.example.alltrailsrestaurantdiscovery.model.Northeast
import com.example.alltrailsrestaurantdiscovery.model.Southwest
import com.squareup.moshi.Json

class Viewport {
    @Json(name = "northeast")
    var northeast: Northeast? = null

    @Json(name = "southwest")
    var southwest: Southwest? = null
}