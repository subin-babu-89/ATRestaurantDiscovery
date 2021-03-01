package com.example.alltrailsrestaurantdiscovery.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alltrailsrestaurantdiscovery.model.PlaceDetails
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: ATRepository) : ViewModel() {

    private var _placeDetails = MutableLiveData<PlaceDetails>()
    val placeDetails: LiveData<PlaceDetails>
        get() = _placeDetails

    fun getPlaceDetails(placeID: String, key: String) {
        viewModelScope.launch {
            val placeDetails1 = repo.getPlaceDetails(placeID, key)
            _placeDetails.value = placeDetails1
        }
    }
}