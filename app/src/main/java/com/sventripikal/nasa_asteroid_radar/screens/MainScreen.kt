package com.sventripikal.nasa_asteroid_radar.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.asteroidList
import com.sventripikal.nasa_asteroid_radar.databinding.FragmentMainScreenBinding
import com.sventripikal.nasa_asteroid_radar.recycler_view.RecyclerViewAdapter


class MainScreen : Fragment() {

    // layout binding
    private lateinit var binding: FragmentMainScreenBinding

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

        // assign layoutManager
        val layoutManager = LinearLayoutManager(requireContext())
        asteroidRecyclerView.layoutManager = layoutManager

        // assign adapter
        val recyclerViewAdapter = RecyclerViewAdapter(asteroidList, requireContext())
        asteroidRecyclerView.adapter = recyclerViewAdapter

        // return inflated layout
        return binding.root
    }
}