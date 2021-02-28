package com.example.alltrailsrestaurantdiscovery.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alltrailsrestaurantdiscovery.model.Result
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(private val repo: ATRepository) : ViewModel() {

    private var _results = MutableLiveData<List<Result>>()
    val result: LiveData<List<Result>>
        get() = _results

    private var _fetchRestaurants = MutableLiveData<Boolean>()
    val fetchRestaurants: LiveData<Boolean>
        get() = _fetchRestaurants

    init {
        _fetchRestaurants.value = true
    }

    fun fetchRestaurantsNearby(location: Location, key: String) {
        viewModelScope.launch {
            try {
                val restaurants =
                    repo.getRestaurants("${location.latitude},${location.longitude}", key)
                _results.value = restaurants
            } catch (exception: Exception) {
                Timber.d("Error : $exception")
            }
        }
    }

    fun favoriteRestaurant(restaurant: Result) {
        viewModelScope.launch {
            val updateRestaurantFavorite = repo.updateRestaurantFavorite(restaurant)
            _results.value = updateRestaurantFavorite
        }
    }

    fun fetchComplete() {
        _fetchRestaurants.value = false
    }
}