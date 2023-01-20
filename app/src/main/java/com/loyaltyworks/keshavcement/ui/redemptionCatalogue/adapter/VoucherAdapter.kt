package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowVoucherBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import java.util.ArrayList

class VoucherAdapter(var onItemClickListener: voucherListAdpaterCallback): RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {

    interface voucherListAdpaterCallback {
        fun onDetailVoucherFromAdapter(itemView: View, position: Int/*,CatalogueVouchers: ObjCatalogueListtt*/)
        fun onRedeemVoucherFromAdapter(itemView: View, position: Int/*CatalogueVouchers: ObjCatalogueListtt, amount: String*/)
    }

    class ViewHolder(val binding: RowVoucherBinding): RecyclerView.ViewHolder(binding.root) {
        val redeem = binding.redeemBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowVoucherBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.redeem.setOnClickListener { v ->
            if (BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onRedeemVoucherFromAdapter(v,position)
        }

        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onDetailVoucherFromAdapter(v,position)
        }
    }

    override fun getItemCount(): Int {
        return 10
    }
}