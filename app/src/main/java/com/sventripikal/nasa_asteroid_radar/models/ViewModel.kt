package com.sventripikal.nasa_asteroid_radar.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sventripikal.nasa_asteroid_radar.utils.listOfAsteroids


// main application viewModel
class ViewModel: ViewModel() {

    // asteroid list [retrieved from Constants]
    private var _asteroidList = MutableLiveData(listOfAsteroids)
    val asteroidList: LiveData<List<Asteroid>>
        get() = _asteroidList

}