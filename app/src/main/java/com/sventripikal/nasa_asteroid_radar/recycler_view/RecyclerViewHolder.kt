package com.sventripikal.nasa_asteroid_radar.recycler_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.databinding.RecyclerListItemBinding
import com.sventripikal.nasa_asteroid_radar.models.Asteroid


// viewHolder extending RecyclerView.ViewHolder
// receives View Binding
class RecyclerViewHolder private constructor(
    private val binding: RecyclerListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    // bind object info to views
    // bind ItemClickListener
    fun bind(asteroid: Asteroid, clickListener: ItemClickListener) {

        // associate data binding variables with objects/classes
        binding.asteroid = asteroid
        binding.itemClickListener = clickListener
        binding.executePendingBindings()
    }


    // public class companion object
    companion object {

        // inflate a specific layout and return a viewholder for it
        fun from(parent: ViewGroup): RecyclerViewHolder {

            // inflate recycler-list-item via view binding
            val binding: RecyclerListItemBinding =
                RecyclerListItemBinding.inflate(LayoutInflater.from(parent.context))

            // return associated viewHolder
            return RecyclerViewHolder(binding)
        }
    }
}