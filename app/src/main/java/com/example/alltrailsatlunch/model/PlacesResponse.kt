package com.example.alltrailsatlunch.model

data class PlacesResponse(
    val html_attributions: List<Any>,
    val results: List<Result>?,
    val status: String
)