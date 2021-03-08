package com.example.alltrailsatlunch.ui

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
import com.example.alltrailsatlunch.R
import com.example.alltrailsatlunch.databinding.MainActivityBinding
import com.example.alltrailsatlunch.db.ATDatabase
import com.example.alltrailsatlunch.network.ApiService
import com.example.alltrailsatlunch.repo.ATRepository
import com.example.alltrailsatlunch.ui.main.AT_VIEW_TYPE
import com.example.alltrailsatlunch.ui.main.RestaurantListViewModel
import com.example.alltrailsatlunch.ui.main.list.RestaurantListFragmentDirections
import com.example.alltrailsatlunch.ui.main.maps.RestaurantMapsFragmentDirections
import com.example.alltrailsatlunch.util.ATViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private const val REQUEST_LOCATION_PERMISSION = 1

/**
 * The activity used to host the other fragments (list and map)
 */
class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var navController: NavController

    private val viewModel: RestaurantListViewModel by viewModels {
        ATViewModelFactory(
            ATRepository(
                ApiService.create(),
                ATDatabase.getInstance(this)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //initiate location request
        viewModel.fetchResults.observe(this, {
            if (it == true) enableMyLocation()
        })

        // initiate restaurant request when location is available
        viewModel.myLocation.observe(this, {
            viewModel.getNearbyRestaurants(it, getString(R.string.maps_api_key))
            viewModel.fetchCompleted()
        })

        // handle toggling of fragments based view switch on the top right corner
        viewModel.viewType.observe(this, {
            if (it == AT_VIEW_TYPE.LIST) {
                binding.viewTypeToggle.setImageResource(R.drawable.ic_map)
                binding.viewTypeToggle.setOnClickListener {
                    navController.navigate(RestaurantListFragmentDirections.listToMap())
                    viewModel.toggleViewType()
                }

            } else {
                binding.viewTypeToggle.setImageResource(R.drawable.ic_list)
                binding.viewTypeToggle.setOnClickListener {
                    navController.navigate(RestaurantMapsFragmentDirections.mapToList())
                    viewModel.toggleViewType()
                }
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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
                        viewModel.setMyLocation(it)
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