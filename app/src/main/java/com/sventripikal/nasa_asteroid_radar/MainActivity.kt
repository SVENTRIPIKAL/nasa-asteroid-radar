package com.sventripikal.nasa_asteroid_radar

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sventripikal.nasa_asteroid_radar.databinding.ActivityMainBinding
import com.sventripikal.nasa_asteroid_radar.models.ApplicationViewModel
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_PAUSE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_RESUME
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_START
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_STOP
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.timber


class MainActivity : AppCompatActivity() {

    // layout binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    // shared viewModel
    private val viewModel: ApplicationViewModel by viewModels { ApplicationViewModel.Factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)


        // inflate main layout
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        // set data binding
        setBindings()

        // add action bar support
        setSupportActionBar(binding.topAppBar)

        // set/find nav controller
        setNavController()

        // onclick listeners
        setUIListeners()

        // set view
        setContentView(binding.root)
    }

    // set data binding
    private fun setBindings() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    // sets up nav controller with action bar
    private fun setNavController() {

        // assign navHostfragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment

        // assign nav controller
        navController = navHostFragment.navController

        // setup nav controller with action bar
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    // ui listeners
    private fun setUIListeners() {

        // menu item onclick listener
        binding.topAppBar.setOnMenuItemClickListener {  menuItem ->

            when (menuItem.itemId) {

                R.id.showWeekAsteroids -> {     // show asteroids of week
                    viewModel.showWeek()
                    true
                }
                R.id.showTodayAsteroids -> {    // show asteroids of today
                    viewModel.showToday()
                    true
                }
                R.id.showSavedAsteroids -> {    // show all asteroids
                    viewModel.showAll()
                    true
                }
                else -> false
            }
        }
    }

    // top app bar up navigation
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }



    /**
     * lifecycle functions
     */
    override fun onStart() {
        super.onStart()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_START", Priority.INFO)
    }

    override fun onResume() {
        super.onResume()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_RESUME", Priority.DEBUG)
    }

    override fun onPause() {
        super.onPause()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_PAUSE", Priority.INFO)
    }

    override fun onStop() {
        super.onStop()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_STOP", Priority.VERBOSE)
    }

    override fun onDestroy() {
        super.onDestroy()

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_DESTROY", Priority.ERROR)
    }
}