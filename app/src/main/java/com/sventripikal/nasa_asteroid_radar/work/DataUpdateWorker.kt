package com.sventripikal.nasa_asteroid_radar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sventripikal.nasa_asteroid_radar.database.AsteroidDatabase
import com.sventripikal.nasa_asteroid_radar.database.getDatabase
import com.sventripikal.nasa_asteroid_radar.repository.AsteroidsRepository


// background worker for updating database data
class DataUpdateWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    private lateinit var database: AsteroidDatabase
    private lateinit var repository: AsteroidsRepository

    // run background work
    override suspend fun doWork(): Result {

        database = getDatabase(applicationContext)
        repository = AsteroidsRepository(database)

        return try {

            // refresh image / asteroids in database
            updateDatabaseEntities()

            // delete older images / asteroids from database
            deleteOldEntities()

            Result.success()    // return success

        } catch (e: retrofit2.HttpException) {

            Result.retry()      // return retry
        }
    }


    // refresh image / asteroids in database
    private suspend fun updateDatabaseEntities() {
        repository.updateImageOfTheDay()
        repository.updateAsteroidsOfTheWeek()
    }


    // delete older images / asteroids from database
    private suspend fun deleteOldEntities() {
        repository.deleteOldImages()
        repository.deleteOldAsteroids()
    }



    // worker name companion object
    companion object {
        const val WORKER_NAME = "DataRefreshWorker"
    }
}