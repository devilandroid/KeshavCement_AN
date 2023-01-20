package com.loyaltyworks.keshavcement.ui.cashTransferApproval.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowCashTransferApprovalBinding

class CashTransferApprovalAdapter: RecyclerView.Adapter<CashTransferApprovalAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowCashTransferApprovalBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferApprovalBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}