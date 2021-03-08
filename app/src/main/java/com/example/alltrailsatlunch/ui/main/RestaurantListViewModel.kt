package com.example.alltrailsatlunch.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.repo.ATRepository
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * ViewModel used for the screens in the app
 */
class RestaurantListViewModel(private val repo: ATRepository) : ViewModel() {

    private var _myLocation = MutableLiveData<Location>()
    val myLocation: LiveData<Location>
        get() = _myLocation

    private var _results = MutableLiveData<List<Result>>()
    val results: LiveData<List<Result>>
        get() = _results

    private var _fetchResults = MutableLiveData<Boolean>()
    val fetchResults: LiveData<Boolean>
        get() = _fetchResults

    private var _viewType = MutableLiveData<AT_VIEW_TYPE>()
    val viewType: LiveData<AT_VIEW_TYPE>
        get() = _viewType


    init {
        _fetchResults.value = true
        _viewType.value = AT_VIEW_TYPE.LIST
    }

    fun getNearbyRestaurants(location: Location, key: String) {
        viewModelScope.launch {
            try {
                val restaurants =
                    repo.getFreshRestaurants("${location.latitude},${location.longitude}", key)
                _results.value = restaurants
            } catch (exception: Exception) {
                Timber.d("Error : $exception")
            }
        }
    }

    fun setStoreFavorite(placeID: String) {
        viewModelScope.launch {
            try {
                _results.value = repo.setStoreFavorite(placeID)
            } catch (exception: java.lang.Exception) {
                Timber.d("Some error $exception")
            }
        }
    }

    fun searchString(inputString: String, key: String) {
        viewModelScope.launch {
            try {
                _myLocation.value?.let {
                    _results.value = repo.getSearchRestaurants(
                        inputString,
                        "circle:3000@${it.latitude},${it.longitude}",
                        key
                    )
                }
            } catch (exception: java.lang.Exception) {
                Timber.d("Some error $exception")
            }
        }
    }

    fun fetchCompleted() {
        _fetchResults.value = false
    }

    fun toggleViewType() {
        _viewType.value =
            if (viewType.value == AT_VIEW_TYPE.LIST) AT_VIEW_TYPE.MAPS else AT_VIEW_TYPE.LIST
    }

    fun setMyLocation(location: Location) {
        _myLocation.value = location
    }
}

enum class AT_VIEW_TYPE {
    MAPS, LIST
}