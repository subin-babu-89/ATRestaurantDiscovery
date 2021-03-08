package com.example.alltrailsatlunch.ui.main.list

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alltrailsatlunch.R
import com.example.alltrailsatlunch.databinding.MainFragmentBinding
import com.example.alltrailsatlunch.db.ATDatabase
import com.example.alltrailsatlunch.network.ApiService
import com.example.alltrailsatlunch.repo.ATRepository
import com.example.alltrailsatlunch.ui.main.RestaurantListViewModel
import com.example.alltrailsatlunch.ui.main.list.RestaurantListAdapter.FavClickListener
import com.example.alltrailsatlunch.util.ATViewModelFactory

/**
 * Fragment used to display the restaurants list
 */
class RestaurantListFragment : Fragment() {

    companion object {
        fun newInstance() = RestaurantListFragment()
    }

    private val viewModel: RestaurantListViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                ApiService.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    private val adapter = RestaurantListAdapter(FavClickListener {
        viewModel.setStoreFavorite(it.place_id)
    },
        RestaurantListAdapter.RestaurantClickListener {
            val gmmIntentUri: Uri =
                Uri.parse("geo:${it.geometry.location.lat},${it.geometry.location.lng}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(mapIntent)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.google_maps_not_installed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.restaurantList.addItemDecoration(
            RestaurantListItemDecorator(
                resources.getDimension(
                    R.dimen.restaurantItemPadding
                ).toInt()
            )
        )
        binding.restaurantList.adapter = adapter

        binding.searchRestaurants.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
//                viewModel.searchString(query, resources.getString(R.string.maps_api_key))
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                (binding.restaurantList.adapter as RestaurantListAdapter).filter.filter(newText)
                return true
            }
        })

        return binding.root
    }

}