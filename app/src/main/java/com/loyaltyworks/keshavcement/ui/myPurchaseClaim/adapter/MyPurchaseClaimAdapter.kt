package com.loyaltyworks.keshavcement.ui.myPurchaseClaim.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowMyPurchaseClaimBinding

class MyPurchaseClaimAdapter : RecyclerView.Adapter<MyPurchaseClaimAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowMyPurchaseClaimBinding): RecyclerView.ViewHolder(binding.root) {

        val status = binding.status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowMyPurchaseClaimBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 10
    }

}
