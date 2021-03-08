package com.example.alltrailsatlunch.ui.main.maps

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alltrailsatlunch.R
import com.example.alltrailsatlunch.databinding.CustomInfoWindowBinding
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.util.BASE_URL
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

/**
 * Custom info window for the maps view
 */
class MapsInfoWindowAdapter(
    private val binding: CustomInfoWindowBinding,
    private val markersList: List<Result>
) : GoogleMap.InfoWindowAdapter {

    override fun getInfoWindow(p0: Marker?): View {
        val markerRestaurant: Result =
            markersList.first { restaurant -> restaurant.name == p0!!.title }
        bind(markerRestaurant)
        return binding.root
    }

    override fun getInfoContents(p0: Marker?): View {
        val markerRestaurant: Result =
            markersList.first { restaurant -> restaurant.name == p0!!.title }
        binding.restaurant = markerRestaurant
        return binding.root
    }

    private fun bind(restaurant: Result) {
        binding.restaurant = restaurant
        binding.restaurantName.text = restaurant.name
        binding.textView.text = restaurant.user_ratings_total.toString()
        val pLevel = restaurant.price_level ?: 1
        val sb = StringBuilder()
        for (i in 0 until pLevel) {
            sb.append("$")
        }
        binding.textView2.text = sb.toString()
        binding.ratingBar.rating = restaurant.rating
        val urlString = restaurant.photos?.get(0)?.photo_reference ?: ""
        val photosApiString =
            "${BASE_URL}photo?maxwidth=400&photoreference=${urlString}&key=${
                binding.imageView.context.resources.getString(
                    R.string.maps_api_key
                )
            }"
        Glide.with(binding.imageView.context).load(photosApiString).apply(
            RequestOptions.centerCropTransform().placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_broken_image)
        ).into(binding.imageView)

        binding.restaurantFav.setImageResource(if (restaurant.favorite) R.drawable.ic_favorite_fill else R.drawable.ic_favorite_empty)
    }
}