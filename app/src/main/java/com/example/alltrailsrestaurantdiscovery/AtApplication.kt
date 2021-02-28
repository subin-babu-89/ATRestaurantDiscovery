package com.example.alltrailsrestaurantdiscovery

import android.app.Application
import timber.log.Timber

class AtApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}