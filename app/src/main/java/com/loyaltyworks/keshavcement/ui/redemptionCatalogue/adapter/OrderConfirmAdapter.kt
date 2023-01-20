package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowConfirmProductBinding

class OrderConfirmAdapter: RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowConfirmProductBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowConfirmProductBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}