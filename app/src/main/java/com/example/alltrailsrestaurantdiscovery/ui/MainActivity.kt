package com.example.alltrailsrestaurantdiscovery.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.databinding.MainActivityBinding
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.example.alltrailsrestaurantdiscovery.ui.main.ATViewModelFactory
import com.example.alltrailsrestaurantdiscovery.ui.main.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private const val REQUEST_LOCATION_PERMISSION = 1

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

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

        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewModel.fetchRestaurants.observe(this, {
            if (it == true) enableMyLocation()
        })

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

//        val appBarConfig = AppBarConfiguration(navController.graph)
//        binding.toolbar.setupWithNavController(navController, appBarConfig)

        // Set up the ActionBar to stay in sync with the NavController
        setupActionBarWithNavController(navController)
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

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}