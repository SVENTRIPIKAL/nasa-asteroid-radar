package com.sventripikal.nasa_asteroid_radar.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.listOfAsteroids
import com.sventripikal.nasa_asteroid_radar.utils.timber


// main application viewModel
class ApplicationViewModel: ViewModel() {


    // asteroid list [retrieved from Constants]
    private var _asteroidList = MutableLiveData(listOfAsteroids)
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList


    // navigation id string
    private var _navigationScreenId = MutableLiveData<String?>()
    val navigationScreenId: LiveData<String?>
        get() = _navigationScreenId


    // assign onClicked item id to navigation screen id
    fun itemClicked(asteroidId: String) {
        _navigationScreenId.value = asteroidId
    }


    // reset navigation screen id to default value
    fun resetNavigationId() {
        timber(TAG, "[${this.javaClass.simpleName}] === [${_navigationScreenId.value}]", Priority.ERROR)

        _navigationScreenId.value = null

        timber(TAG, "[${this.javaClass.simpleName}] === [${_navigationScreenId.value}]", Priority.ERROR)
    }


    /**
     * lifecycle functions
     */
    init {
        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)
    }

    override fun onCleared() {
        super.onCleared()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_DESTROY", Priority.ERROR)
    }


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