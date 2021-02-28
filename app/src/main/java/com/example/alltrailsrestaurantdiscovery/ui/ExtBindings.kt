package com.example.alltrailsrestaurantdiscovery.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alltrailsrestaurantdiscovery.model.Result

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