package com.sventripikal.nasa_asteroid_radar.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.sventripikal.nasa_asteroid_radar.databinding.FragmentMainScreenBinding
import com.sventripikal.nasa_asteroid_radar.models.ApplicationViewModel
import com.sventripikal.nasa_asteroid_radar.recycler_view.ItemClickListener
import com.sventripikal.nasa_asteroid_radar.recycler_view.RecyclerViewAdapter
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_CREATE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_DESTROY
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_PAUSE
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_RESUME
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_START
import com.sventripikal.nasa_asteroid_radar.utils.MESSAGE_STOP
import com.sventripikal.nasa_asteroid_radar.utils.Priority
import com.sventripikal.nasa_asteroid_radar.utils.TAG
import com.sventripikal.nasa_asteroid_radar.utils.timber


// main screen fragment
class MainScreen : Fragment() {


    // late inits
    private lateinit var recyclerView: RecyclerView                     // recyclerView
    private lateinit var lifecycleOwner: LifecycleOwner                 // lifecycleOwner
    private lateinit var binding: FragmentMainScreenBinding             // layout binding
    private lateinit var newRecyclerViewAdapter: RecyclerViewAdapter    // recyclerViewAdapter


    // reference to viewModel instance
    private val viewModel = ApplicationViewModel.getInstance()          // viewModel


    // inflate layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)


        // inflate layout
        binding = FragmentMainScreenBinding.inflate(inflater)


        // create adapter
        // adapter receives ItemClickListener class to handle click events
        newRecyclerViewAdapter = RecyclerViewAdapter( ItemClickListener {

                // on item click run functions & pass clicked item
                asteroid ->
                    viewModel.updateDetailsScreenAsteroid(asteroid)
                    viewModel.itemClicked(asteroid.id)
        })


        // bind layout views
        setUIBindings()


        // apply UI observers
        setUIObservers()


        // return inflated layout
        return binding.root
    }


    // bind layout views
    private fun setUIBindings() {

        // assign lifecycle owner
        lifecycleOwner = this

        // assign recycler view
        recyclerView = binding.asteroidRecyclerView

        // update the layout recyclerView adapter with created adapter
        recyclerView.adapter = newRecyclerViewAdapter
    }


    // apply UI observers
    private fun setUIObservers() {

        /**
         * TO DO: create viewModel.apply block
         */

        // observe viewModel list changes
        viewModel.asteroidList.observe(lifecycleOwner, Observer { list ->

            // allow adapter to update list changes
            list?.let {
                newRecyclerViewAdapter.submitList(it)
            }
        })


        // observe id changes & navigate to destination
        viewModel.navigationItemId.observe(lifecycleOwner, Observer{ id ->

            // if id is not null
            id?.let {

                // navigate to destination using safeArgs
                val action = MainScreenDirections.actionMainScreenToDetailsFragment()
                findNavController().navigate(action)

                // reset navigation id value in viewModel
                viewModel.resetNavigationItemId()
            }
        })
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