package com.example.alltrailsatlunch.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alltrailsatlunch.repo.ATRepository
import com.example.alltrailsatlunch.ui.main.RestaurantListViewModel

/**
 * View model factory for the app
 */
class ATViewModelFactory(private val repo: ATRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantListViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}