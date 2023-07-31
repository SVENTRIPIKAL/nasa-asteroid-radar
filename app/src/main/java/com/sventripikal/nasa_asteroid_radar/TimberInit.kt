package com.sventripikal.nasa_asteroid_radar

import android.app.Application
import timber.log.Timber


// Timber initializer
class TimberInit: Application() {
    override fun onCreate() {
        super.onCreate()
        // enable Timber logging
        Timber.plant(Timber.DebugTree())
    }
}