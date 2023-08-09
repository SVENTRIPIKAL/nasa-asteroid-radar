package com.sventripikal.nasa_asteroid_radar.repository

import androidx.lifecycle.LiveData
import com.sventripikal.nasa_asteroid_radar.database.AsteroidDatabase
import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import com.sventripikal.nasa_asteroid_radar.models.ImageOfTheDay
import com.sventripikal.nasa_asteroid_radar.network.AsteroidApi
import com.sventripikal.nasa_asteroid_radar.network.convertToListOfAsteroids
import com.sventripikal.nasa_asteroid_radar.network.extractImageOfTheDay
import com.sventripikal.nasa_asteroid_radar.network.getDemoQueryDemoKey
import com.sventripikal.nasa_asteroid_radar.network.getTodayWeekQueryApiKey
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.timber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


// repository for accessing the network
// bridges Network, Database, and ViewModel
class AsteroidsRepository(private val database: AsteroidDatabase) {

    // Triple startDate / endDate / apiKey used to query asteroids for the week
    private val startEndDateApiKey = getTodayWeekQueryApiKey()

    // asteroid live data list pulled from database for display - pulls this week only
    val asteroidListRepo: LiveData<List<Asteroid>> = database.databaseDao
                                                        .getAsteroidsOfTheWeek(
                                                            startEndDateApiKey.first,
                                                            startEndDateApiKey.second
                                                        )

    // image of the day live data pulled from database for display - pulls today only
    val imageOfTheDayRepo: LiveData<ImageOfTheDay?> = database.databaseDao
                                                        .getImageOfTheDay(
                                                            startEndDateApiKey.first
                                                        )

    // coroutine function for updating database for the week
    suspend fun refreshImageOfTheDay() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // makes query call using api_key to NASA API
                val networkQueryString = AsteroidApi.retrofitService
                                                        .getImageOfTheDay(startEndDateApiKey.third)

                // converts response String to Image of the Day
                val imageOfTheDay: ImageOfTheDay? = extractImageOfTheDay(networkQueryString)

                // inserts into database
                database.databaseDao.insertImage(imageOfTheDay)

                timber(TAG, "[$this] === ${imageOfTheDay?.title} added", Priority.DEBUG)

                // success message
                val message = "Success: ${imageOfTheDay?.title} received/cached"

                timber(TAG, "[$this] === $message", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }


    // coroutine function for updating database for the week
    suspend fun refreshAsteroidsOfTheWeek() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // makes query call using dates/api_key to NASA API
                val networkQueryString = AsteroidApi.retrofitService.getAsteroidFeedForWeek(
                    startDate = startEndDateApiKey.first,
                    endDate = startEndDateApiKey.second,
                    apiKey = startEndDateApiKey.third
                )

                // converts response String to Asteroid objects
                val list = convertToListOfAsteroids(networkQueryString)

                // updates database
                list.forEach {
                    database.databaseDao.insertAsteroid(it)

                    timber(TAG, "[$this] === ${it.name} added", Priority.DEBUG)
                }

                // success message
                val message = "Success: ${list.size} asteroids received/cached"

                timber(TAG, "[$this] === $message", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }


    // coroutine function for updating database with DEMO objects
    suspend fun refreshDemoDatabase() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // retreives demo dates & demo key
                val demoStringTriple = getDemoQueryDemoKey()

                // makes query call using demo dates/api_key to NASA API
                val demoNetworkQueryString = AsteroidApi.retrofitService.getAsteroidFeedForWeek(
                    startDate = demoStringTriple.first,
                    endDate = demoStringTriple.second,
                    apiKey = demoStringTriple.third
                )

                // converts response String to Asteroid objects
                val list = convertToListOfAsteroids(demoNetworkQueryString)

                // updates database
                list.forEach {
                    database.databaseDao.insertAsteroid(it)

                    timber(TAG, "[$this] === ${it.name} DEMO added", Priority.DEBUG)
                }

                // success message
                val message = "Success: ${list.size} DEMO asteroids received/cached"

                timber(TAG, "[$this] === $message", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }
}
