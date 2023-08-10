package com.sventripikal.nasa_asteroid_radar.utils

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sventripikal.nasa_asteroid_radar.work.DataUpdateWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit


// timber initializer
class TimberInit: Application() {

    // coroutine scope for background work
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {

        super.onCreate()

        // enable Timber logging
        Timber.plant(Timber.DebugTree())

        // run work in background thread
        runBackgroundWork()
    }

    // runs coroutine work
    private fun runBackgroundWork() {
        applicationScope.launch {
            dailyUpdateWork()
        }
    }

    // sets worker for database refresh daily
    private fun dailyUpdateWork() {

        // worker constraints - device charging & on unmetered connection
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()


        // worker update interval - once a day with added constraints
        val refreshInterval =
            PeriodicWorkRequestBuilder<DataUpdateWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()


        // create worker to execute work
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            DataUpdateWorker.WORKER_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            refreshInterval
        )
    }
}