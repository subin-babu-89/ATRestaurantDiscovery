package com.example.alltrailsatlunch.ui.main.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.alltrailsatlunch.databinding.RestaurantSearchItemBinding

import com.example.alltrailsatlunch.model.Result
import java.util.*

/**
 * Restaurant adapter for the restaurants recycler view
 */
class RestaurantListAdapter(
    private val favClickListener: FavClickListener,
    private val itemClickListener: RestaurantClickListener
) :
    ListAdapter<Result, RestaurantListAdapter.ViewHolder>(RESULT_COMPARATOR), Filterable {

    var mListRef: List<Result>? = null
    var mFilteredList: List<Result>? = null

    companion object {
        val RESULT_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun submitList(list: MutableList<Result>?) {
        if (mListRef == null) {
            mListRef = list
        }
        super.submitList(list)
    }

    class ViewHolder(private val binding: RestaurantSearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val restaurantFav = binding.restaurantFav

        fun bind(result: Result) {
            binding.restaurant = result
            binding.executePendingBindings()
        }
    }

    class FavClickListener(val onFavClick: (result: Result) -> Unit) {
        fun favClick(result: Result) {
            if (!result.favorite) {
                onFavClick(result)
            }
        }
    }

    class RestaurantClickListener(val onRestaurantClick: (result: Result) -> Unit) {
        fun itemClick(result: Result) {
            onRestaurantClick(result)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RestaurantSearchItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener { itemClickListener.itemClick(item) }
        holder.restaurantFav.setOnClickListener { favClickListener.favClick(item) }
        holder.bind(item)
    }

    /**
     * Currently filtering only based on name for the restaurants
     */
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {

                val charString = charSequence.toString()

                if (charString.isEmpty()) {
                    mFilteredList = mListRef
                } else {
                    mListRef?.let {
                        val filteredList = arrayListOf<Result>()
                        for (baseDataItem in mListRef!!) {
                            if (charString.toLowerCase(Locale.ENGLISH) in baseDataItem.name.toLowerCase(
                                    Locale.ENGLISH
                                )
                            ) {
                                filteredList.add(baseDataItem)
                            }
                        }

                        mFilteredList = filteredList
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filterResults.values?.let {
                    mFilteredList = it as ArrayList<Result>
                    submitList(mFilteredList as ArrayList<Result>)
                }
            }
        }
    }

}