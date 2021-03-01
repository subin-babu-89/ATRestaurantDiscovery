package com.example.alltrailsrestaurantdiscovery.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alltrailsrestaurantdiscovery.model.PlaceDetails

@Dao
interface PlaceDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeDetails: PlaceDetails)

    @Query("SELECT * FROM place_details WHERE placeId = :placeid LIMIT 1")
    suspend fun placeDetail(placeid: String): PlaceDetails?

    @Query("DELETE FROM place_details")
    suspend fun clearAll()
}