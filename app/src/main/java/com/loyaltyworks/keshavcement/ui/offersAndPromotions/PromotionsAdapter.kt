package com.loyaltyworks.keshavcement.ui.offersAndPromotions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowPromotionsBinding

class PromotionsAdapter(var onItemClick: OnItemClick) : RecyclerView.Adapter<PromotionsAdapter.ViewHolder>() {

    interface OnItemClick{

        fun onItemClicked()
    }

    class ViewHolder(val binding: RowPromotionsBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = RowPromotionsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
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
