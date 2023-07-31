package com.sventripikal.nasa_asteroid_radar.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// adapter implementing ListAdapter<T, VH>( DiffUtil() )
class RecyclerViewAdapter : ListAdapter<Asteroid, RecyclerViewHolder>( DiffCallback() ) {


    // inflate a layout and return a viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {

        // inflate from ViewHolder companion function
        return RecyclerViewHolder.from(parent)
    }


    // bind/update views with asteroid info
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        // get asteroid from asteroid list by position via ListAdapter
        val asteroid = getItem(position)

        // bind object info to views from within ViewHolder
        holder.bind(asteroid)
    }
}