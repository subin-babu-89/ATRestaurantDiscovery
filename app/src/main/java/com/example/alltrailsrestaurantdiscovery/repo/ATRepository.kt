package com.example.alltrailsrestaurantdiscovery.repo

import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.model.PlaceDetails
import com.example.alltrailsrestaurantdiscovery.model.Result
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import kotlinx.coroutines.delay

class ATRepository(private val service: PeopleServiceAPI, private val database: ATDatabase) {

    suspend fun getRestaurants(locationString: String, key: String): List<Result> {
        var counter = 1
        var nextPageToken = ""
        val results = mutableListOf<Result>()
        while (counter > 0) {
            val placesResult = service.getPlacesResult(
                locationString = locationString,
                key = key,
                pageToken = nextPageToken
            )
            placesResult.results?.let {
                results.addAll(it)
            }
            placesResult.nextPageToken?.let {
                nextPageToken = it
            } ?: run { counter = 0 }
            counter--
            delay(2000L)
        }
        database.resultDao().clearAll()
        database.resultDao().insertAll(results)
        return database.resultDao().allResourceTypes()
    }

    suspend fun getRestaurants(): List<Result> {
        return database.resultDao().allResourceTypes()
    }

    suspend fun updateRestaurantFavorite(restaurant: Result): List<Result> {
        restaurant.placeId?.let { database.resultDao().setFavorite(it) }
        return database.resultDao().allResourceTypes()
    }

    suspend fun getPlaceDetails(placeId: String, key: String): PlaceDetails {
        var placeDetail = database.placeDetailsDao().placeDetail(placeId)
        if (placeDetail == null) {
            val placeDetails = service.getPlaceDetails(placeId, key)
            placeDetails.placeDetails?.let {
                database.placeDetailsDao().insert(it)
                placeDetail = it
            }
        }
        return placeDetail!!
    }
}