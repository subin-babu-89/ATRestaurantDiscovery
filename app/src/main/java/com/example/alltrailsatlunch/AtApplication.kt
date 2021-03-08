package com.example.alltrailsatlunch

import android.app.Application
import timber.log.Timber

/**
 * Subclassed to provide the timber logging library
 */
class AtApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}