package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class Resources {
    @Json(name = "html_attributions")
    var htmlAttributions: List<Any>? = null

    @Json(name = "next_page_token")
    var nextPageToken: String? = null

    @Json(name = "results")
    var results: List<Result>? = null

    @Json(name = "status")
    var status: String? = null
}