package com.sventripikal.nasa_asteroid_radar.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.getCurrentDateString
import com.sventripikal.nasa_asteroid_radar.utils.listOfAsteroids
import com.sventripikal.nasa_asteroid_radar.utils.timber


// main application viewModel
class ApplicationViewModel: ViewModel() {


    // asteroid list [retrieved from Constants]
    private var _asteroidList = MutableLiveData(listOfAsteroids)
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList



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

    // get current date string
    fun getCloseApproachDate(): String {
        return getCurrentDateString()
    }

    // get detail asteroid magnitude string
    fun getAbsoluteMagnitude(): String {
        return "${_detailsScreenAsteroid.value!!.absoluteMagnitude} au"
    }

    // get detail asteroid diameter string
    fun getEstimatedDiameter(): String {
        return "${_detailsScreenAsteroid.value!!.estimatedDiameterMax} km"
    }

    // get detail asteroid hazard potential string
    fun getHazardPotential(): String {
        return when(_detailsScreenAsteroid.value!!.isPotentiallyHazardousAsteroid) {
            true -> "Potentially Hazardous"
            else -> "Not Hazardous"
        }
    }

    // get detail asteroid velocity string
    fun getRelativeVelocity(): String {
        return "${_detailsScreenAsteroid.value!!.kilometersPerSecond} km/s"
    }

    // get detail asteroid distance string
    fun getDistanceFromEarth(): String {
        return "${_detailsScreenAsteroid.value!!.astronomical} au"
    }



    /**
     *  LIFECYCLE FUNCTIONS
     */
    init {
        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)
    }

    override fun onCleared() {
        super.onCleared()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_DESTROY", Priority.ERROR)
    }



    /**
     *  COMPANION OBJECT
     */
    // used for creating viewModel using singleton method
    companion object {

        // instance of this viewModel
        private lateinit var instance: ApplicationViewModel

        // create/assign this viewModel to instance
        // return instance
        fun getInstance(): ApplicationViewModel {
            instance = if (::instance.isInitialized) instance else ApplicationViewModel()
            return instance
        }
    }
}