package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowCustomerBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class CustomerSelectionAdapter(var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CustomerSelectionAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onCustListItemClickResponse(itemView: View, position: Int)
    }

    class ViewHolder(val binding: RowCustomerBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onCustListItemClickResponse(v,position)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}