package com.sventripikal.nasa_asteroid_radar.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// sets an asteroid ID into a TextView
@BindingAdapter("setAsteroidName")
fun TextView.setAsteroidName(asteroid: Asteroid?) {

    asteroid?.let {

        text = asteroid.id
    }
}

// sets the current date into a TextView
@BindingAdapter("setCurrentDateFormatted")
fun TextView.setCurrentDateFormatted(asteroid: Asteroid?) {

    asteroid?.let {

        text = getCurrentDateString()
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