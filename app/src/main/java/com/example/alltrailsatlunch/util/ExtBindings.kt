package com.example.alltrailsatlunch.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.alltrailsatlunch.R
import com.example.alltrailsatlunch.model.Result
import com.example.alltrailsatlunch.ui.main.list.RestaurantListAdapter

@BindingAdapter("setRestaurants")
fun setRestaurantsToList(recyclerView: RecyclerView, data: List<Result>?) {
    val adapter = recyclerView.adapter as RestaurantListAdapter
    data?.let {
        adapter.submitList(it.toMutableList())
    }
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

@BindingAdapter("setURLImage")
fun bindUrlImage(imageView: ImageView, urlString: String) {
    val photosApiString =
        "${BASE_URL}photo?maxwidth=400&photoreference=${urlString}&key=${
            imageView.context.resources.getString(
                R.string.maps_api_key
            )
        }"
    Glide.with(imageView.context).load(photosApiString).apply(
        RequestOptions.centerCropTransform().placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
    )
        .into(imageView)
}