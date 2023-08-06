package com.sventripikal.nasa_asteroid_radar.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.sventripikal.nasa_asteroid_radar.R
import com.sventripikal.nasa_asteroid_radar.databinding.FragmentDetailsScreenBinding
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


// details screen fragment
class DetailsScreen : Fragment() {


    // binding for layout
    private lateinit var binding: FragmentDetailsScreenBinding


    // reference to viewModel instance
    private val viewModel = ApplicationViewModel.getInstance()


    // inflate layout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        timber(TAG, "[${this.javaClass.simpleName}] === $MESSAGE_CREATE", Priority.VERBOSE)

        // Inflate the layout for this fragment
        binding = FragmentDetailsScreenBinding.inflate(inflater)

        // bind viewModel instance to data
        binding.viewModel = viewModel

        // add clickListener for Help Icon
        binding.absoluteMagnitudeHelpIcon.setOnClickListener {


            /**
             * TO DO:
             *      +_scrim needs to be fixed / background does not dim
             *      +_dialog needs to be customized
             */
            // basic material dialog
            MaterialAlertDialogBuilder(requireContext(), R.style.DialogTheme)
                .setMessage("The astronomical unit (au) is a unit of length, roughly the distance from " +
                        "Earth to the Sun, and equal to about 150 million kilometers (93 million miles).")
                .setPositiveButton("Accept") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // return root view
        return binding.root
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