package com.sventripikal.nasa_asteroid_radar.recycler_view

import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// click listener that receives data & runs a function
class ItemClickListener(val clickListener: (asteroidId: String) -> Unit) {

    // funtion called when a view is clicked
    fun onClick(asteroid: Asteroid) = clickListener(asteroid.id)
}