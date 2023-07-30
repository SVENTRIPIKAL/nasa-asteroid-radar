package com.sventripikal.nasa_asteroid_radar.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.MainActivity
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.models.Asteroid

/**
 * TO-DO:
 *  +_add comments
 *
 *  +_extract resources
 *
 *  +_assign styles / theme
 *
 *  +_Create Details Screen
 *
 *  +_Update view onClick listeners
 */

// adapter implementing RecyclerView.Adapter
class RecyclerViewAdapter(
    private var asteroidList: List<Asteroid>,
    private val context: MainActivity
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    // inflate recycler item view and return ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        // inflate item view
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_list_item, parent, false)

        // return viewHolder with view
        return RecyclerViewHolder(itemView)
    }


    // update views within root view by referencing IDs
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        // get views by ID
        val asteroidName: TextView = holder.itemView.findViewById(R.id.asteroidName)
        val currentDate: TextView = holder.itemView.findViewById(R.id.currentDate)
        val icon: ImageView = holder.itemView.findViewById(R.id.dangerStatus)

        // get item from asteroid list by position
        val item = asteroidList[position]


        // assign item info to views
        asteroidName.text = item.id

        // temp string [get current date function needed here]
        currentDate.text = "07/30/2023"


        // determine color/icon by hazard
        when (item.isPotentiallyHazardousAsteroid) {
            true -> {
                icon.setImageResource(R.drawable.round_sentiment_very_dissatisfied_24)
                icon.setColorFilter(ContextCompat.getColor(context, R.color.red))
            }
            false -> {
                icon.setImageResource(R.drawable.round_sentiment_very_satisfied_24)
                icon.setColorFilter(ContextCompat.getColor(context, R.color.green))
            }
        }


    }

    // return size of list
    override fun getItemCount(): Int {
        return asteroidList.size
    }
}