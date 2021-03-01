package com.example.alltrailsrestaurantdiscovery.ui

import android.location.Location
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alltrailsrestaurantdiscovery.R
import com.example.alltrailsrestaurantdiscovery.model.Result
import com.example.alltrailsrestaurantdiscovery.network.PeopleServiceAPI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@BindingAdapter("setRestaurants")
fun bindRestaurants(recyclerView: RecyclerView, data: List<Result>?) {
    val adapter = recyclerView.adapter as RestaurantsAdapter
    adapter.submitList(data)
}

@BindingAdapter("setPriceLevel")
fun bindPricingLevel(textView: TextView, priceLevel: Int?) {
    val pLevel = priceLevel ?: 1
    val sb = StringBuilder()
    for (i in 0 until pLevel) {
        sb.append("$")
    }
    textView.text = sb.toString()
}

@BindingAdapter("urlImage")
fun bindURLImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context)
        .load(
            PeopleServiceAPI.BASE_URL + "photo?maxwidth=400&photoreference=" + url + "&key=" + imageView.context.resources.getString(
                R.string.maps_api_key
            )
        ).apply(
            RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        )
        .into(imageView)
}


@BindingAdapter(value = ["setMarkers", "setMyLocation"], requireAll = true)
fun bindMapMarkers(mapView: MapView?, resultList: List<Result>?, myLocation: Location?) {
    mapView?.let { mapview ->
        mapview.onCreate(Bundle())
        mapview.getMapAsync {
            it.uiSettings.setAllGesturesEnabled(true)
            it.uiSettings.isMyLocationButtonEnabled = true
            resultList?.let {
                it.forEach { result ->
//                    val currentMarker = LatLng(result.)
                }
            }
            // below lin is use to zoom our camera on map.
            it.animateCamera(CameraUpdateFactory.zoomTo(18.0f));
            myLocation?.let { location ->
                it.moveCamera(
                    CameraUpdateFactory.newLatLng(
                        LatLng(
                            location.latitude,
                            location.longitude
                        )
                    )
                )
            }
            it.moveCamera(CameraUpdateFactory.newLatLng(LatLng(43.1, -87.9)))

        }
    }
}