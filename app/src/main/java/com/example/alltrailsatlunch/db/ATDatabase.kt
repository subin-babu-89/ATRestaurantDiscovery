package com.example.alltrailsatlunch.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.util.APP_DB_NAME

@Database(
    entities = [Result::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringListTypeConverters::class)
/**
 * Database(Room) class used in the application for local persistence
 */
abstract class ATDatabase : RoomDatabase() {

    abstract fun resultDao(): ResultDao

    companion object {
        @Volatile
        private var INSTANCE: ATDatabase? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context): ATDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ATDatabase::class.java,
                APP_DB_NAME
            ).build()
        }
    }
}