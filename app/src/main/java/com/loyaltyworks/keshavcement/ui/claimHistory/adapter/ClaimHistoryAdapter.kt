package com.loyaltyworks.keshavcement.ui.claimHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowClaimHistoryBinding

class ClaimHistoryAdapter: RecyclerView.Adapter<ClaimHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowClaimHistoryBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowClaimHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}