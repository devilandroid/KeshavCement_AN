package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowCashTransferBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class CashTransferAdapter(var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CashTransferAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onListItemClickResponse(itemView: View, position: Int)
        fun onRedeemBtnClickResponse(itemView: View, position: Int)
    }

    class ViewHolder(val binding: RowCashTransferBinding): RecyclerView.ViewHolder(binding.root) {
        val redeemBtn = binding.redeemBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onListItemClickResponse(v,position)
        }

        holder.redeemBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onRedeemBtnClickResponse(v,position)
        }

    }

    override fun getItemCount(): Int {
        return 10
    }
}