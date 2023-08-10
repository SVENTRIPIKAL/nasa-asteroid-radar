package com.sventripikal.nasa_asteroid_radar.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
    private lateinit var binding: FragmentMainScreenBinding             // layout binding
    private lateinit var newRecyclerViewAdapter: RecyclerViewAdapter    // recyclerViewAdapter


    // shared viewModel
    private val viewModel: ApplicationViewModel by activityViewModels{ ApplicationViewModel.Factory }


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

                // on item click run functions & pass clicked items
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

        // lifecycle owner
        binding.lifecycleOwner = requireActivity()

        // bind viewModel instance
        binding.viewModel = viewModel

        // assign recycler view
        recyclerView = binding.asteroidRecyclerView

        // update the layout recyclerView adapter with created adapter
        recyclerView.adapter = newRecyclerViewAdapter
    }


    // apply UI observers
    private fun setUIObservers() {


        // viewModel block
        viewModel.apply {

            // observe viewModel list changes
            asteroidList.observe(viewLifecycleOwner, Observer { list ->

                // allow adapter to update list changes
                list?.let {
                    newRecyclerViewAdapter.submitList(it)
                }
            })


            // observe id changes & navigate to destination
            navigationItemId.observe(viewLifecycleOwner, Observer{ id ->

                // if id is not null
                id?.let {

                    // navigate to destination using safeArgs
                    val action = MainScreenDirections.actionMainScreenToDetailsFragment()
                    findNavController().navigate(action)

                    // reset navigation id value in viewModel
                    resetNavigationItemId()
                }
            })
        }
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