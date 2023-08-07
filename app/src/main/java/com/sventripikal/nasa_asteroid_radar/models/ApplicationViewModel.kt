package com.sventripikal.nasa_asteroid_radar.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sventripikal.nasa_asteroid_radar.network.AsteroidApi
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.fakeDataRequest
import com.sventripikal.nasa_asteroid_radar.utils.getListOfAsteroids
import com.sventripikal.nasa_asteroid_radar.utils.timber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// main application viewModel
class ApplicationViewModel: ViewModel() {


    // asteroid list
    private var _asteroidList = MutableLiveData<List<Asteroid>>()
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



    /**
     *  NETWORK FUNCTIONS
     */
    private var _networkResponse = MutableLiveData<String>()
    val networkResponse: LiveData<String>
        get() = _networkResponse


    // function to submit network request
    private fun queryNetworkResponse() {

        AsteroidApi.retrofitService.getAsteroidsForTheWeek().enqueue(object: Callback<String> {

            // on success when fetching request
            override fun onResponse(call: Call<String>, response: Response<String>) {

                // assign response body to network value
                _networkResponse.value = response.body()
            }

            // on failure when fetching request
            override fun onFailure(call: Call<String>, t: Throwable) {

                // assign response failure message to network value
                _networkResponse.value = "Failure: ${t.message}"
            }

        })
    }


    /**
     *  LIFECYCLE FUNCTIONS
     */
    init {
        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)

        // launch viewModel coroutine
        viewModelScope.launch {

            // convert Json String to Asteroid list asyncly from FakeDataRequest.kt file
            val list = withContext(Dispatchers.IO) {
                getListOfAsteroids(fakeDataRequest)
            }

            // update asteroid list
            _asteroidList.postValue(list)
        }
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