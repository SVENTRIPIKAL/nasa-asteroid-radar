package com.sventripikal.nasa_asteroid_radar

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.sventripikal.nasa_asteroid_radar.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // layout binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate main layout
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        // set view
        setContentView(binding.root)
    }
}