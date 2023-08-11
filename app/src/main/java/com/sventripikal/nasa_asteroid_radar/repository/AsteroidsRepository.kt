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
import com.sventripikal.nasa_asteroid_radar.network.getWeekPriorDate
import com.sventripikal.nasa_asteroid_radar.utils.FilterBy
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


    // image of the day live data pulled from database for display - pulls today only
    val imageOfTheDayRepo: LiveData<ImageOfTheDay> = database.databaseDao
                                                        .getImageOfTheDay(
                                                            startEndDateApiKey.first
                                                        )


    // returns a list of live data from database according to filter type
    fun getList(filter: FilterBy): LiveData<List<Asteroid>> {

        return when (filter) {
            FilterBy.WEEK -> {
                // asteroid live data list pulled from database - pulls this week only
                database.databaseDao.getThisWeeksAsteroids(
                    startEndDateApiKey.first,
                    startEndDateApiKey.second
                )
            }
            FilterBy.TODAY -> {
                // asteroid live data list pulled from database - pulls today only
                database.databaseDao
                    .getTodaysAsteroids(startEndDateApiKey.first)
            }
            // asteroid live data list pulled from database - pulls all
            FilterBy.ALL -> database.databaseDao.getAllSavedAsteroids()
        }
    }


    // network query function for updating image of the day
    suspend fun updateImageOfTheDay() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // send get request to NASA API
                val networkQueryString = AsteroidApi.retrofitService
                                                        .getImageOfTheDay(startEndDateApiKey.third)

                // convert response String to Image of the Day
                val imageOfTheDay: ImageOfTheDay? = extractImageOfTheDay(networkQueryString)

                // save non-null objects to database
                if (imageOfTheDay != null) {

                    // insert image into database
                    database.databaseDao.insertImage(imageOfTheDay)

                    // success message
                    val message = "Success: ${imageOfTheDay.title} received/cached"

                    timber(TAG, "[$this] === $message", Priority.DEBUG)

                }
                else timber(TAG, "[$this] === Non-Image Discarded", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }


    // network query function for updating asteroids for the week
    suspend fun updateAsteroidsOfTheWeek() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // send get request to NASA API
                val networkQueryString = AsteroidApi.retrofitService.getAsteroidFeedForWeek(
                    startDate = startEndDateApiKey.first,
                    endDate = startEndDateApiKey.second,
                    apiKey = startEndDateApiKey.third
                )

                // convert response String to Asteroid list
                val list = convertToListOfAsteroids(networkQueryString)

                // update database with asteroids
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


    // deletes old images from database
    suspend fun deleteOldImages() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // delete images before today
                database.databaseDao.deleteImagesBeforeToday(startEndDateApiKey.first)

                // success message
                val message = "Success: images before ${startEndDateApiKey.first} deleted"

                timber(TAG, "[$this] === $message", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }


    // deletes old asteroids from database [more than a week old]
    suspend fun deleteWeekOldAsteroids() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // get week prior date
                val weekPrior = getWeekPriorDate()

                // delete asteroids that are more than a week old from today
                database.databaseDao.deleteWeekOldAsteroids(weekPrior)

                // success message
                val message = "Success: asteroids older than $weekPrior deleted"

                timber(TAG, "[$this] === $message", Priority.VERBOSE)

            } catch (t: Throwable) {

                // failure message
                val message = "Failure: ${t.message}"

                timber(TAG, "[$this] === $message", Priority.ERROR)
            }
        }
    }


    // function for adding Demo asteroids list to database
    suspend fun addDemoQueryAsteroids() {

        // runs work in background thread
        withContext(Dispatchers.IO) {

            try {

                // retreive demo dates & demo key
                val demoStringTriple = getDemoQueryDemoKey()

                // send get request to NASA API
                val demoNetworkQueryString = AsteroidApi.retrofitService.getAsteroidFeedForWeek(
                    startDate = demoStringTriple.first,
                    endDate = demoStringTriple.second,
                    apiKey = demoStringTriple.third
                )

                // convert response String to Asteroid list
                val list = convertToListOfAsteroids(demoNetworkQueryString)

                // update database
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
