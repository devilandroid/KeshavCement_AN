package com.loyaltyworks.keshavcement.ui.offersAndPromotions.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowPromotionsBinding

class PromotionsAdapter(var onPromotionItemClick: OnPromotionItemClick) : RecyclerView.Adapter<PromotionsAdapter.ViewHolder>() {

    interface OnPromotionItemClick{

        fun onOffersItemClicked()
    }

    class ViewHolder(val binding: RowPromotionsBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPromotionsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener{

            onPromotionItemClick.onOffersItemClicked()
        }


    }

    override fun getItemCount(): Int {
        return 10
    }


}
