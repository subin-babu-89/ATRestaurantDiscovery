package com.example.alltrailsatlunch.repo

import com.example.alltrailsatlunch.db.ATDatabase
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.network.ApiService

/**
 * Repository for the all trails lunch app
 */
class ATRepository(private val service: ApiService, private val database: ATDatabase) {

    /**
     * Fetch results from the local db
     */
    suspend fun getRestaurants(): List<Result> {
        return database.resultDao().allResourceTypes()
    }

    /**
     * get results from the network and persist them locally before presenting it on the view
     */
    suspend fun getFreshRestaurants(locationString: String, key: String): List<Result> {
        var localRestaurantList = getRestaurants()
        if (localRestaurantList.isNullOrEmpty()) {
            val placeResult = service.getNearbyResult(locationString = locationString, key = key)
            placeResult.results?.let {
                database.resultDao().clearAll()
                database.resultDao().insertAll(it)
            }
            localRestaurantList = getRestaurants()
        }
        return localRestaurantList
    }

    /**
     * search restaurants by query
     *
     * TODO : add search json model and possible persists results locally
     */
    suspend fun getSearchRestaurants(
        inpuString: String,
        locationBias: String,
        key: String
    ): List<Result> {
        val findPlaceFromText = service.findPlaceFromText(
            inputString = inpuString,
            locationBias = locationBias,
            key = key
        )
        findPlaceFromText.results?.let {
            database.resultDao().insertAll(it)
        }
        return if (findPlaceFromText.results.isNullOrEmpty()) emptyList<Result>() else {
            findPlaceFromText.results
        }
    }

    /**
     * Set the result as fav. Since there is no api to update this info online, this is only done locally
     */
    suspend fun setStoreFavorite(placeID: String): List<Result> {
        database.resultDao().setFavorite(placeID)
        return getRestaurants()
    }
}