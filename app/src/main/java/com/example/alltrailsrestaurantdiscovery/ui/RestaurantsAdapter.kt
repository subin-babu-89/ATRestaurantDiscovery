package com.example.alltrailsrestaurantdiscovery.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alltrailsrestaurantdiscovery.databinding.RestaurantSearchItemBinding
import com.example.alltrailsrestaurantdiscovery.model.Result

class RestaurantsAdapter(private val favoriteClickListener: ClickListener) :
    ListAdapter<Result, RestaurantsAdapter.RestaurantViewHolder>(RESTAURANT_COMPARATOR) {


    companion object {
        val RESTAURANT_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                oldItem.placeId == newItem.placeId
        }
    }

    class RestaurantViewHolder(private val binding: RestaurantSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val favorite: ImageView = binding.imageView2
        fun bind(restaurant: Result) {
            binding.restaurant = restaurant
            binding.executePendingBindings()
        }
    }

    class ClickListener(val clickListener: (restaurant: Result) -> Unit) {
        fun onFavoriteClick(restaurant: Result) {
            if (!restaurant.isFavorite) {
                clickListener(restaurant)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder(
            RestaurantSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = getItem(position)
        holder.favorite.setOnClickListener { favoriteClickListener.onFavoriteClick(restaurant) }
        holder.bind(restaurant)
    }
}