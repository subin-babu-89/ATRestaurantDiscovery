package com.example.alltrailsrestaurantdiscovery

import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.model.Result
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI

class ATRepository(private val service : PeopleServiceAPI, private val database : ATDatabase) {

    suspend fun getRestaurants(locationString : String, key : String) : List<Result> {
        database.resultDao().clearAll()
        val placesResult = service.getPlacesResult(locationString = locationString, key = key)
        placesResult.results?.let {
            database.resultDao().insertAll(it)
        }
        return database.resultDao().allResourceTypes()
    }
}