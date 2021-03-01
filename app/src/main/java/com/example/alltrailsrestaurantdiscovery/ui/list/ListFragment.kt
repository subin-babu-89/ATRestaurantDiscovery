package com.example.alltrailsrestaurantdiscovery.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.databinding.FragmentListBinding
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.example.alltrailsrestaurantdiscovery.ui.RestaurantItemListDecorator
import com.example.alltrailsrestaurantdiscovery.ui.RestaurantsAdapter
import com.example.alltrailsrestaurantdiscovery.ui.main.ATViewModelFactory
import com.example.alltrailsrestaurantdiscovery.ui.main.MainFragmentDirections
import com.example.alltrailsrestaurantdiscovery.ui.main.MainViewModel

class ListFragment : Fragment() {
    private val viewModel: MainViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                PeopleServiceAPI.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    private val adapter = RestaurantsAdapter(
        RestaurantsAdapter.ClickListener({
            val action =
                MainFragmentDirections.actionMainFragmentToDetailFragment(it.placeId!!)
            findNavController().navigate(action)
        }, {
            viewModel.favoriteRestaurant(it)
        })
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.restaurantList.adapter = adapter
        binding.restaurantList.addItemDecoration(
            RestaurantItemListDecorator(
                resources.getDimension(R.dimen.articleItemPadding).toInt()
            )
        )
        return binding.root
    }
}