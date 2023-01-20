package com.loyaltyworks.keshavcement.ui.pendingClaimRequest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowPendingClaimRequestBinding

class PendingClaimRequestAdapter: RecyclerView.Adapter<PendingClaimRequestAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowPendingClaimRequestBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPendingClaimRequestBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}