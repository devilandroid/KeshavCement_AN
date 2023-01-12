package com.loyaltyworks.keshavcement.ui.myPurchaseClaim

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowMyPurchaseClaimBinding

class MyPurchaseClaimAdapter : RecyclerView.Adapter<MyPurchaseClaimAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowMyPurchaseClaimBinding): RecyclerView.ViewHolder(binding.root) {

        val status = binding.status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = RowMyPurchaseClaimBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(position%2 == 1){

            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.pending_yellow1))
            holder.status.text = "Pending"

        }else if(position%3 == 1){

            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
            holder.status.text = "Rejected"

        }else{

            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.green))
            holder.status.text = "Approved "

        }

    }

    override fun getItemCount(): Int {
        return 10
    }

}
