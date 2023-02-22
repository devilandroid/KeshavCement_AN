package com.loyaltyworks.keshavcement.ui.cashTransferHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCashTransferHistoryBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.utils.AppController

class CashTransferHistoryAdapter(val objCatalogueRedemReqList: List<ObjCatalogueRedemReq>): RecyclerView.Adapter<CashTransferHistoryAdapter.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(val binding: RowCashTransferHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val customerType = binding.customerType
        val customerName = binding.customerName
        val status = binding.status
        val cashTransferName = binding.cashTransferName
        val points = binding.points
        val remarks = binding.remarks

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rewardTransDetails = objCatalogueRedemReqList[position]

        holder.cashTransferName.text = rewardTransDetails.productName.toString()
        holder.date.text = AppController.dateAPIFormat(rewardTransDetails.jRedemptionDate.toString().split(" ")[0])
        holder.points.text = rewardTransDetails.redemptionPoints.toString()
        holder.customerName.text = rewardTransDetails.fullName.toString()
        holder.customerType.text = rewardTransDetails.membertype.toString()


        if (rewardTransDetails.remarks.isNullOrEmpty()){
            holder.remarks.text = rewardTransDetails.remarks
        }else{
            holder.remarks.text = "-"
        }

        if (rewardTransDetails.status == 1) {
            holder.status.text = "Approved"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        }else if (rewardTransDetails.status == 5) {
            holder.status.text = "Rejected"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        }

    }

    override fun getItemCount(): Int {
        return objCatalogueRedemReqList.size
    }
}