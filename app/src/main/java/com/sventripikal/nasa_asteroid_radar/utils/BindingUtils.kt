package com.sventripikal.nasa_asteroid_radar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// loads image of the day into an ImageView
@BindingAdapter("setImageOfTheDay")
fun ImageView.setImageOfTheDay(url: String?) {

    if (url != null)  Picasso.get().load(url)
                                    .placeholder(R.drawable.loading_animation)
                                    .error(R.drawable.ic_broken_image)
                                    .into(this)
}


// sets an asteroid ID into a TextView
@BindingAdapter("setAsteroidName")
fun TextView.setAsteroidName(asteroid: Asteroid?) {

    asteroid?.let {

        text = asteroid.name
    }
}


// sets an asteroid hazard icon/color into an ImageView
@BindingAdapter("setHazardIcon")
fun ImageView.setHazardIcon(asteroid: Asteroid?) {

    asteroid?.let {

       contentDescription = when (it.isPotentiallyHazardousAsteroid) {

            // hazardous - red
            true -> {
                setImageResource(R.drawable.round_sentiment_very_dissatisfied_24)
                setColorFilter(ContextCompat.getColor(context, R.color.red))
                resources.getText(R.string.potentially_hazardous)
            }

            // non-hazardous - green
            false -> {
                setImageResource(R.drawable.round_sentiment_very_satisfied_24)
                setColorFilter(ContextCompat.getColor(context, R.color.green))
                resources.getText(R.string.not_hazardous)
            }
        }
    }
}


// sets an asteroid image into an ImageView
// assigns image content description
@BindingAdapter("setAsteroidImage")
fun ImageView.setAsteroidImage(asteroid: Asteroid?) {

    asteroid?.let {

        contentDescription = when (it.isPotentiallyHazardousAsteroid) {

            // hazardous
            true -> {
                setImageResource(R.drawable.asteroid_hazardous)
                resources.getText(R.string.potentially_hazardous)
            }

            // non-hazardous
            false -> {
                setImageResource(R.drawable.asteroid_safe)
                resources.getText(R.string.not_hazardous)
            }
        }
    }
}


// sets asteroid close approach date into a TextView
@BindingAdapter("setCloseApproachDate")
fun TextView.setCloseApproachDate(asteroid: Asteroid?) {

    asteroid?.let {

        text = asteroid.closeApproachDate
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