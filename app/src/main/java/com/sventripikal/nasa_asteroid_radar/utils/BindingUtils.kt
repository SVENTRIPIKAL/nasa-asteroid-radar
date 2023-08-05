package com.sventripikal.nasa_asteroid_radar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.models.Asteroid



// sets an asteroid ID into a TextView
@BindingAdapter("setAsteroidId")
fun TextView.setAsteroidId(asteroid: Asteroid?) {

    asteroid?.let {

        text = asteroid.id
    }
}


// sets an asteroid hazard icon/color into an ImageView
@BindingAdapter("setHazardIcon")
fun ImageView.setHazardIcon(asteroid: Asteroid?) {

    asteroid?.let {

        when (it.isPotentiallyHazardousAsteroid) {

            // hazardous - red
            true -> {
                setImageResource(R.drawable.round_sentiment_very_dissatisfied_24)
                setColorFilter(ContextCompat.getColor(context, R.color.red))
            }

            // non-hazardous - green
            false -> {
                setImageResource(R.drawable.round_sentiment_very_satisfied_24)
                setColorFilter(ContextCompat.getColor(context, R.color.green))
            }
        }
    }
}


// sets an asteroid image into an ImageView
@BindingAdapter("setAsteroidImage")
fun ImageView.setAsteroidImage(asteroid: Asteroid?) {

    asteroid?.let {

        when (it.isPotentiallyHazardousAsteroid) {

            // hazardous
            true -> {
                setImageResource(R.drawable.asteroid_hazardous)
            }

            // non-hazardous
            false -> {
                setImageResource(R.drawable.asteroid_safe)
            }
        }
    }
}


// sets the current date into a TextView
@BindingAdapter("setCloseApproachDate")
fun TextView.setCloseApproachDate(asteroid: Asteroid?) {

    asteroid?.let {

        text = getCurrentDateString()
    }
}


// sets asteroid magnitude into a TextView
@BindingAdapter("setAbsoluteMagnitude")
fun TextView.setAbsoluteMagnitude(asteroid: Asteroid?) {

    asteroid?.let {

        val magnitude = "${asteroid.absoluteMagnitude} au"

        text = magnitude
    }
}


// sets asteroid diameter into a TextView
@BindingAdapter("setEstimatedDiameter")
fun TextView.setEstimatedDiameter(asteroid: Asteroid?) {

    asteroid?.let {

        val diameter = "${asteroid.estimatedDiameterMax} km"

        text = diameter
    }
}


// sets asteroid velocity into a TextView
@BindingAdapter("setRelativeVelocity")
fun TextView.setRelativeVelocity(asteroid: Asteroid?) {

    asteroid?.let {

        val velocity = "${asteroid.kilometersPerSecond} km/s"

        text = velocity
    }
}


// sets asteroid distance into a TextView
@BindingAdapter("setDistanceFromEarth")
fun TextView.setDistanceFromEarth(asteroid: Asteroid?) {

    asteroid?.let {

        val velocity = "${asteroid.astronomical} au"

        text = velocity
    }
}