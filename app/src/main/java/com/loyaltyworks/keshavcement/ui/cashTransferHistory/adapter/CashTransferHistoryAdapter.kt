package com.loyaltyworks.keshavcement.ui.cashTransferHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowCashTransferHistoryBinding

class CashTransferHistoryAdapter: RecyclerView.Adapter<CashTransferHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowCashTransferHistoryBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}