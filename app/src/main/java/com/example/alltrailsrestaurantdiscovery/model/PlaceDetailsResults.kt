package com.example.alltrailsrestaurantdiscovery.model

import com.squareup.moshi.Json

class PlaceDetailsResults(@Json(name = "result") var placeDetails: PlaceDetails? = null)