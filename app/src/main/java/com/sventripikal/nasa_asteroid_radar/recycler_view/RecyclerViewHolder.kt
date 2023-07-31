package com.sventripikal.nasa_asteroid_radar.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


// viewHolder extending RecyclerView.ViewHolder
class RecyclerViewHolder private constructor(itemView: View, private val context: Context)
    : RecyclerView.ViewHolder(itemView) {

    // get views by ID & associate with viewholder fields
    private val asteroidNameTextView: TextView = itemView.findViewById(R.id.asteroidName)
    private val currentDateTextView: TextView = itemView.findViewById(R.id.currentDate)
    private val iconImageView: ImageView = itemView.findViewById(R.id.dangerStatus)


    // bind object info to views
    fun bind(asteroid: Asteroid) {

        // assign object info to private viewholder fields
        assignInfoToViews(asteroid)
    }


    // assigns object info to views
    private fun assignInfoToViews(asteroid: Asteroid) {

        // assign TextView asteroid name
        asteroidNameTextView.text = asteroid.id

        // assign TextView current date
        currentDateTextView.text = getCurrentDateString()

        // assign ImageView icon/color by asteroid hazard potential
        setHazardPotentialIcon(asteroid)
    }


    // returns current date as formatted string
    private fun getCurrentDateString(): String {

        // create calendar instance
        val calendar = Calendar.getInstance()

        // get date string pattern from strings.xml
        val dateStringPattern = context.getString(R.string.yyyy_mm_dd)

        // create formatter with date string pattern
        val formatter = SimpleDateFormat(dateStringPattern, Locale.getDefault())

        // return formatted date as string
        return formatter.format(calendar.time).toString()
    }


    // sets icon/color by asteroid hazard potential
    private fun setHazardPotentialIcon(asteroid: Asteroid) {

        when (asteroid.isPotentiallyHazardousAsteroid) {

            // hazardous - red
            true -> {
                iconImageView.setImageResource(R.drawable.round_sentiment_very_dissatisfied_24)
                iconImageView.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }

            // non-hazardous - green
            false -> {
                iconImageView.setImageResource(R.drawable.round_sentiment_very_satisfied_24)
                iconImageView.setColorFilter(ContextCompat.getColor(context, R.color.green))
            }
        }
    }



    // public class companion object
    companion object {

        // inflates a specific layout and returns a viewholder for it
        fun from(parent: ViewGroup): RecyclerViewHolder {

            // inflate recycler-list-item root view
            val itemView: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_list_item, parent, false)

            // return associated viewHolder
            return RecyclerViewHolder(itemView, parent.context)
        }
    }
}