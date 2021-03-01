package com.example.alltrailsrestaurantdiscovery.ui.maps

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.alltrailsrestaurantdiscovery.databinding.FragmentMapsBinding
import com.example.alltrailsrestaurantdiscovery.db.ATDatabase
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.example.alltrailsrestaurantdiscovery.repo.ATRepository
import com.example.alltrailsrestaurantdiscovery.ui.main.ATViewModelFactory
import com.example.alltrailsrestaurantdiscovery.ui.main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentMapsBinding
    private lateinit var map: GoogleMap
    private lateinit var myLocation: Location

    private val viewModel: MainViewModel by activityViewModels {
        ATViewModelFactory(
            ATRepository(
                PeopleServiceAPI.create(),
                ATDatabase.getInstance(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapsBinding.inflate(inflater)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)

        viewModel.result.observe(viewLifecycleOwner, {
            it?.forEach { result ->
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            result.geometry?.location?.lat!!,
                            result.geometry?.location?.lng!!
                        )
                    ).icon(
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
                    ).visible(true)
                )
            }
        })

        viewModel.myLocation.observe(viewLifecycleOwner, {
            myLocation = it
            //Move the camera to the user's location and zoom in!
            moveToLocation()
        })

        viewModel.resetView.observe(viewLifecycleOwner, {
            if (it == true) {
                viewModel.fetchRestaurantsNearbyLocally()
                moveToLocation()
                viewModel.resetView(false)
            }
        })
        return binding.root
    }

    fun moveToLocation() {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    myLocation.latitude,
                    myLocation.longitude
                ), 12.0f
            )
        )
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            map = googleMap
        }
        map.uiSettings.isMyLocationButtonEnabled = false
        /*
       //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
       */

        // Updates the location and zoom of the MapView
        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);*/
        /*
       //in old Api Needs to call MapsInitializer before doing any CameraUpdateFactory call
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
       */

        // Updates the location and zoom of the MapView
        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
        map.animateCamera(cameraUpdate);*/
    }

    override fun onResume() {
        binding.mapView.onResume()
        super.onResume()
    }


    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}