package com.sventripikal.nasa_asteroid_radar.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.databinding.FragmentMainScreenBinding
import com.sventripikal.nasa_asteroid_radar.models.ViewModel
import com.sventripikal.nasa_asteroid_radar.recycler_view.RecyclerViewAdapter


// main screen fragment
class MainScreen : Fragment() {

    // layout binding
    private lateinit var binding: FragmentMainScreenBinding


    // inflate layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // inflate layout
        binding = FragmentMainScreenBinding.inflate(inflater)


        // set image of the day
        val imageOfTheDay = binding.imageOfTheDay
        imageOfTheDay.setImageResource(R.drawable.ic_launcher_background)


        // assign recycler view from layout
        val asteroidRecyclerView: RecyclerView = binding.asteroidRecyclerView


        // create viewModel
        val viewModel = ViewModel()


        // create/assign adapter
        val recyclerViewAdapter = RecyclerViewAdapter()
        asteroidRecyclerView.adapter = recyclerViewAdapter


        // observe viewModel list changes
        viewModel.asteroidList.observe(viewLifecycleOwner, Observer { list ->

            // allow adapter to update list changes
            list?.let {
                recyclerViewAdapter.submitList(it)
            }
        })


        // return inflated layout
        return binding.root
    }
}