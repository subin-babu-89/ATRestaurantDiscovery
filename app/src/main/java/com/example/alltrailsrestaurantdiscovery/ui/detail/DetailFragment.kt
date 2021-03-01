package com.example.alltrailsrestaurantdiscovery.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.databinding.DetailFragmentBinding
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.example.alltrailsrestaurantdiscovery.ui.main.ATViewModelFactory

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private val viewModel: DetailViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                PeopleServiceAPI.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DetailFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        viewModel.getPlaceDetails(
            DetailFragmentArgs.fromBundle(requireArguments()).placeID,
            resources.getString(R.string.maps_api_key)
        )
        return binding.root
    }
}