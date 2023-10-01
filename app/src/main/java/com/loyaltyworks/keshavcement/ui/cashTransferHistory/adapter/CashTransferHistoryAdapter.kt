package com.loyaltyworks.keshavcement.ui.cashTransferHistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCashTransferHistoryBinding
import com.loyaltyworks.keshavcement.model.LstCustomerCashTransferedDetailTransferHistory
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.utils.AppController

class CashTransferHistoryAdapter(val lstCustomerCashTransferedDetailTransferHistory: List<LstCustomerCashTransferedDetailTransferHistory>): RecyclerView.Adapter<CashTransferHistoryAdapter.ViewHolder>() {

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
        val rewardTransDetails = lstCustomerCashTransferedDetailTransferHistory[position]

        holder.cashTransferName.text = "₹ " +   rewardTransDetails.transferedPointsinAmount.toString()
        holder.date.text = AppController.dateAPIFormat(rewardTransDetails.createdDate.toString().split(" ")[0])
        holder.points.text = rewardTransDetails.points.toString()
        holder.customerName.text = rewardTransDetails.customerName.toString()
        holder.customerType.text = rewardTransDetails.customerType.toString()


        if (!rewardTransDetails.remarks.isNullOrEmpty()){
            holder.remarks.text = rewardTransDetails.remarks
        }else{
            holder.remarks.text = "-"
        }

        if (rewardTransDetails.cashTransferedStatus.equals("Approved",true)) {
            holder.status.text = rewardTransDetails.cashTransferedStatus
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        }else if (rewardTransDetails.cashTransferedStatus.equals("Rejected",true)) {
            holder.status.text = rewardTransDetails.cashTransferedStatus
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        }

    }

    override fun getItemCount(): Int {
        return lstCustomerCashTransferedDetailTransferHistory.size
    }
}