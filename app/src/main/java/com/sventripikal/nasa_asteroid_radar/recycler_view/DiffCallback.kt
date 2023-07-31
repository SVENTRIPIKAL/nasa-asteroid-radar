package com.sventripikal.nasa_asteroid_radar.recycler_view

import androidx.recyclerview.widget.DiffUtil
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// class implementing DiffUtil - checks for differences in list items
class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {

    // checks if item data is equal
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    // checks if items are equal
    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }
}