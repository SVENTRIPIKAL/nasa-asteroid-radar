package com.sventripikal.nasa_asteroid_radar.models

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.sventripikal.nasa_asteroid_radar.database.getDatabase
import com.sventripikal.nasa_asteroid_radar.repository.AsteroidsRepository
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.timber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// main application viewModel
class ApplicationViewModel(application: Application): ViewModel() {


    /**
     *  MAIN SCREEN
     */
    // item id observed for recyclerView onClick navigation
    private var _navigationItemId = MutableLiveData<String?>()
    val navigationItemId: LiveData<String?>
        get() = _navigationItemId

    // assign onClicked item id to recyclerView observed variable
    fun itemClicked(asteroidId: String) {
        _navigationItemId.value = asteroidId
    }

    // reset recyclerView observed item id to allow for back navigation
    fun resetNavigationItemId() {
        timber(TAG, "[${this.javaClass.simpleName}] === [${_navigationItemId.value}]", Priority.ERROR)

        _navigationItemId.value = null

        timber(TAG, "[${this.javaClass.simpleName}] === [${_detailsScreenAsteroid.value}]", Priority.DEBUG)
        timber(TAG, "[${this.javaClass.simpleName}] === [${_navigationItemId.value}]", Priority.ERROR)
    }



    /**
     *  DETAILS SCREEN
     */
    // asteroid used for Details Screen
    private var _detailsScreenAsteroid = MutableLiveData<Asteroid>()
    val detailsScreenAsteroid: LiveData<Asteroid>
        get() = _detailsScreenAsteroid

    // update Details Screen asteroid
    fun updateDetailsScreenAsteroid(asteroid: Asteroid) {
        _detailsScreenAsteroid.value = asteroid
    }



    /**
     *  NETWORK / DATABASE FUNCTIONS
     */
    // create database
    private val database = getDatabase(application)

    // create repository
    private val asteroidRepository = AsteroidsRepository(database)

    // links to & observes asteroidListRepo
    val asteroidList: LiveData<List<Asteroid>> = asteroidRepository.asteroidListRepo

    // links to & observes imageOfTheDayRepo
    val imageOfTheDay: LiveData<ImageOfTheDay> = asteroidRepository.imageOfTheDayRepo

    // query NASA API asteroid feed
    private fun executeStartUpJobs() {

        // coroutine scope to handle suspend function calls
        viewModelScope.launch(Dispatchers.IO) {

            // updates image of the day and asteroid list for the week
            asteroidRepository.updateImageOfTheDay()
            asteroidRepository.updateAsteroidsOfTheWeek()


            // deletes all old files from database before today on separate thread
            viewModelScope.launch(Dispatchers.IO) {
                asteroidRepository.deleteOldImages()
                asteroidRepository.deleteOldAsteroids()
            }
        }
    }



    /**
     *  LIFECYCLE FUNCTIONS
     */
    init {
        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)

        // refresh database and delete old files
        executeStartUpJobs()
    }

    override fun onCleared() {
        super.onCleared()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_DESTROY", Priority.ERROR)
    }


    /**
     *  COMPANION OBJECT
     */
    // used for creating viewModel factory
    companion object {

        // viewmodel factory for passing application context for database
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[APPLICATION_KEY])

                return ApplicationViewModel(application) as T
            }
        }
    }
}