package com.example.alltrailsrestaurantdiscovery.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alltrailsrestaurantdiscovery.model.Result

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
abstract class ATDatabase : RoomDatabase() {

    abstract fun resultDao() : ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ATDatabase? = null

        fun getInstance(context: Context): ATDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): ATDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ATDatabase::class.java,
                "all_trails_restaurants"
            ).build()
        }
    }
}