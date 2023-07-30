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


// adapter implementing RecyclerView.Adapter
class RecyclerViewAdapter(
    private var asteroidList: List<Asteroid>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    // lateinit fields
    private lateinit var asteroid: Asteroid
    private lateinit var iconImageView: ImageView
    private lateinit var currentDateTextView: TextView
    private lateinit var asteroidNameTextView: TextView


    // inflate layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        // inflate item view
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_list_item, parent, false)

        // return viewHolder with view
        return RecyclerViewHolder(itemView)
    }


    // bind/update views within root view by referencing IDs
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        // get views by ID
        asteroidNameTextView = holder.itemView.findViewById(R.id.asteroidName)
        currentDateTextView = holder.itemView.findViewById(R.id.currentDate)
        iconImageView = holder.itemView.findViewById(R.id.dangerStatus)

        // get asteroid from asteroid list by position
        asteroid = asteroidList[position]

        // assign asteroid info to views
        assignInfoToViews()
    }


    // return size of list
    override fun getItemCount(): Int {
        return asteroidList.size
    }


    // assigns asteroid info to views
    private fun assignInfoToViews() {

        // assign TextView asteroid name
        asteroidNameTextView.text = asteroid.id

        // assign TextView current date
        currentDateTextView.text = getCurrentDateString()

        // assign ImageView icon/color by asteroid hazard potential
        setHazardPotentialIcon()
    }

    // returns current date as formatted string
    private fun getCurrentDateString(): String {

        // create calendar instance
        val calendar = Calendar.getInstance()

        // get date string pattern from strings.xml
        val dateStringPattern = context.getString(R.string.mm_dd_yyyy)

        // create formatter with date string pattern
        val formatter = SimpleDateFormat(dateStringPattern, Locale.getDefault())

        // return formatted date as string
        return formatter.format(calendar.time).toString()
    }


    // sets icon/color by asteroid hazard potential
    private fun setHazardPotentialIcon() {

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
}