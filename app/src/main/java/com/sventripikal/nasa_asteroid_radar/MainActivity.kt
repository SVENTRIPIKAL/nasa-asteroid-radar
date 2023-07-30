package com.sventripikal.nasa_asteroid_radar

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.databinding.ActivityMainBinding
import com.sventripikal.nasa_asteroid_radar.models.Asteroid
import com.sventripikal.nasa_asteroid_radar.recycler_view.RecyclerViewAdapter


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
class MainActivity : AppCompatActivity() {

    // sample/temp asteroid list
    private var asteroidList: List<Asteroid> = listOf(
        Asteroid(
            id = "0000001",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000002",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000003",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = true,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000004",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000005",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000006",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = true,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000007",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000008",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = false,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000009",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = true,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
        Asteroid(
            id = "0000010",
            absoluteMagnitude = 20.48,
            estimatedDiameterMax = 0.4764748465,
            isPotentiallyHazardousAsteroid = true,
            kilometersPerSecond = "18.127936605",
            astronomical = "0.3027469593"
        ),
    )

    // layout binding
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflate main layout
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))


        // assign recycler view from layout
        val asteroidRecyclerView: RecyclerView = binding.asteroidRecyclerView


        // assign a layoutManager
        val layoutManager = LinearLayoutManager(this)
        asteroidRecyclerView.layoutManager = layoutManager

        
        // assign an Adapter
        val recyclerViewAdapter = RecyclerViewAdapter(asteroidList, this)
        asteroidRecyclerView.adapter = recyclerViewAdapter


        // set view
        setContentView(binding.root)
    }
}