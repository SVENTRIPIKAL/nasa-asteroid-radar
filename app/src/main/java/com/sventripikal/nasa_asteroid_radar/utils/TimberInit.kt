package com.sventripikal.nasa_asteroid_radar.utils

import android.app.Application
import timber.log.Timber


// timber initializer
class TimberInit: Application() {

    override fun onCreate() {

        super.onCreate()

        // enable Timber logging
        Timber.plant(Timber.DebugTree())
    }
}