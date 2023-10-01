package com.loyaltyworks.keshavcement.ui.myRedemption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCashRedemptionBinding
import com.loyaltyworks.keshavcement.databinding.RowMyRedemptionsBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.utils.AppController

class CashRedemptionAdapter(val objCatalogueRedemReqList: List<ObjCatalogueRedemReq>): RecyclerView.Adapter<CashRedemptionAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(binding: RowCashRedemptionBinding): RecyclerView.ViewHolder(binding.root) {
        val catalogueType = binding.catalogueType
        val status = binding.status
        val requestedTo = binding.requestedTo
        val cashTransferDate = binding.cashTransferDate
        val cashPoint = binding.cashPoint
        val cashValue = binding.value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashRedemptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rewardTransDetails = objCatalogueRedemReqList[position]

        holder.catalogueType.text =  "Cash Transfer"

        holder.requestedTo.text =  rewardTransDetails.cashTransferedTo
        holder.cashTransferDate.text =  AppController.dateAPIFormat(rewardTransDetails.jRedemptionDate.toString().split(" ")[0])
        holder.cashPoint.text =  rewardTransDetails.cashTransferedInAmount
        holder.cashValue.text =  rewardTransDetails.cashTransferedPoints
        holder.status.text =  rewardTransDetails.cashTransferedStatus


        if (rewardTransDetails.cashTransferedStatus!!.equals("Pending",true)) {
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))

        } else if (rewardTransDetails.cashTransferedStatus!!.equals("Approved",true)) {
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.cashTransferedStatus!!.equals("Rejected",true)) {
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        }


    }

    override fun getItemCount(): Int {
        return objCatalogueRedemReqList.size
    }
}