package com.example.alltrailsrestaurantdiscovery.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.example.alltrailsrestaurantdiscovery.ui.main.ATViewModelFactory
import com.example.alltrailsrestaurantdiscovery.ui.main.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private const val REQUEST_LOCATION_PERMISSION = 1

class MainActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val viewModel: MainViewModel by viewModels {
        ATViewModelFactory(
            ATRepository(
                PeopleServiceAPI.create(),
                ATDatabase.getInstance(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel.fetchRestaurants.observe(this, {
            if (it == true) enableMyLocation()
        })
    }

    private fun enableMyLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    location?.let {
                        viewModel.fetchRestaurantsNearby(
                            it,
                            resources.getString(R.string.maps_api_key)
                        )
                        viewModel.fetchComplete()
                    }
                }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }
}