package com.example.alltrailsrestaurantdiscovery.ui.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.databinding.MainFragmentBinding
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.google.android.gms.maps.MapView
import timber.log.Timber


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                PeopleServiceAPI.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    private lateinit var binding: MainFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.view_switch) {
            binding.viewFlipper.showNext()
            if (binding.viewFlipper.currentView is MapView){
                viewModel.resetView(true)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}