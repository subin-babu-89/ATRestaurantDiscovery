package com.example.alltrailsatlunch.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.alltrailsatlunch.model.Result

@Dao
/**
 * Dao for the result objects stored in the app local db
 */
interface ResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(resourceTypes: List<Result>)

    @Query("SELECT * FROM restaurants")
    suspend fun allResourceTypes(): List<Result>

    @Query("DELETE FROM restaurants")
    suspend fun clearAll()

    @Query("UPDATE restaurants SET favorite = 1 WHERE place_id = :placeId")
    suspend fun setFavorite(placeId: String)
}