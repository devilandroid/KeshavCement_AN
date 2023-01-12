package com.loyaltyworks.keshavcement.ui.lodgeQuery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowQueryListingBinding
import com.loyaltyworks.keshavcement.ui.offersAndPromotions.PromotionsAdapter

class QueryListingAdapter(var onItemClick: PromotionsAdapter.OnItemClick) : RecyclerView.Adapter<QueryListingAdapter.ViewHolder>() {

    interface OnItemClick{

        fun onItemClicked()
    }

    class ViewHolder(val binding: RowQueryListingBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowQueryListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener{

            onItemClick.onItemClicked()
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

}
