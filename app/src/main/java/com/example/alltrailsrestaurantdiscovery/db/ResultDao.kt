package com.example.alltrailsrestaurantdiscovery.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alltrailsrestaurantdiscovery.model.Result

@Dao
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resourceTypes: List<Result>)

    @Query("SELECT * FROM restaurant")
    suspend fun allResourceTypes(): List<Result>

    @Query("DELETE FROM restaurant")
    suspend fun clearAll()
}