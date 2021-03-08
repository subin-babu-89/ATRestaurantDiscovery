package com.example.alltrailsatlunch.ui.main.maps

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alltrailsatlunch.R
import com.example.alltrailsatlunch.databinding.CustomInfoWindowBinding
import com.example.alltrailsatlunch.databinding.FragmentMapsBinding
import com.example.alltrailsatlunch.db.ATDatabase
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.network.ApiService
import com.example.alltrailsatlunch.repo.ATRepository
import com.example.alltrailsatlunch.ui.main.RestaurantListViewModel
import com.example.alltrailsatlunch.util.ATViewModelFactory
import com.example.alltrailsatlunch.util.DEFAULT_MAP_ZOOM_LEVEL
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import timber.log.Timber

/**
 * Fragment used to display the restaurants maps as pins
 */
class RestaurantMapsFragment : Fragment() {

    var myLocation = LatLng(0.0, 0.0)
    var restaurantList: List<Result> = mutableListOf()
    var gMap: GoogleMap? = null

    var mSelectedMarker: Marker? = null

    private lateinit var inflaterS: LayoutInflater

    private val viewModel: RestaurantListViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                ApiService.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        gMap = googleMap
        // Customise the styling of the base map using a JSON object defined
        // in a raw resource file.
        try {
            val success = gMap?.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireContext(), R.raw.style_json
                )
            )
            if (success == false) {
                Timber.d("Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Timber.d("Can't find style. Error: $e")
        }

        // set custom info adapter for the map view
        gMap?.setInfoWindowAdapter(
            MapsInfoWindowAdapter(
                CustomInfoWindowBinding.inflate(
                    inflaterS
                ), restaurantList
            )
        )

        // set list of restaurants as pins on the map view
        for (restaurant in restaurantList) {
            val location = restaurant.geometry.location
            val restaurantLocation = LatLng(location.lat, location.lng)
            gMap?.addMarker(
                MarkerOptions().position(restaurantLocation).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.ic_inactive)
                ).title(restaurant.name)
            )
        }

        //set my location visible on the map
        gMap?.isMyLocationEnabled = true

        // pan and zoom the camera to the current location with zoom level set to 14
        gMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, DEFAULT_MAP_ZOOM_LEVEL))

        // set correct map pins icon depending on the selected status of the marker
        gMap?.setOnMarkerClickListener {
            mSelectedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_inactive))
            mSelectedMarker = it
            mSelectedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_active));
            false
        }
        gMap?.setOnMapClickListener {
            mSelectedMarker?.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_inactive))
            mSelectedMarker = null
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflaterS = inflater
        val binding = FragmentMapsBinding.inflate(inflater)
        viewModel.results.observe(viewLifecycleOwner, { results ->
            restaurantList = results
            gMap?.let {
                it.clear()
                for (restaurant in restaurantList) {
                    val location = restaurant.geometry.location
                    val restaurantLocation = LatLng(location.lat, location.lng)
                    it.addMarker(
                        MarkerOptions().position(restaurantLocation).title(restaurant.name)
                    )
                }
                it.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
            }
        })
        viewModel.myLocation.observe(viewLifecycleOwner, {
            myLocation = LatLng(it.latitude, it.longitude)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}