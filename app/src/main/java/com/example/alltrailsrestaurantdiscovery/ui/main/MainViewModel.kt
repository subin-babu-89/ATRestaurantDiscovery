package com.example.alltrailsrestaurantdiscovery.ui.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alltrailsrestaurantdiscovery.ATRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repo: ATRepository) : ViewModel() {

    fun fetchRestaurantsNearby(location: Location, key: String) {
        viewModelScope.launch {
            try {
                repo.getRestaurants("${location.latitude},${location.longitude}", key)
            } catch (exception: Exception) {

            }
        }
    }

}