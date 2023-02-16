package com.loyaltyworks.keshavcement.ui.claimHistory.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowClaimHistoryBinding
import com.loyaltyworks.keshavcement.model.CustomerBasicInfoJsonPurchaseClaim
import com.loyaltyworks.keshavcement.utils.AppController

class ClaimHistoryAdapter(val customerBasicInfoListJson: List<CustomerBasicInfoJsonPurchaseClaim>): RecyclerView.Adapter<ClaimHistoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowClaimHistoryBinding):RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val customerType = binding.customerType
        val customerName = binding.customerName
        val status = binding.status
        val prodName = binding.prodName
        val quantity = binding.quantity
        val approvedQuantity = binding.approvedQuantity
        val approvedQuantityLayout = binding.approvedQuantityLayout
        val remarks = binding.remarks
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowClaimHistoryBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = customerBasicInfoListJson[position]

        holder.customerName.text = data.requestTo
        holder.quantity.text = data.quantity.toString()
        holder.prodName.text = data.productName

        holder.customerType.text = data.customerType

        holder.status.text = data.status

        if (data.trxnDate != null){
            holder.date.text = AppController.dateAPIFormat(data.trxnDate.split(" ")[0])
        }else{
            holder.date.text = "-"
        }

        if (data.status.equals("Pending",true)){
            holder.approvedQuantityLayout.visibility = View.GONE
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(Color.parseColor("#B6C90F"))

        }else if (data.status.equals("Approved",true)){
            holder.approvedQuantityLayout.visibility = View.VISIBLE
            holder.approvedQuantity.text = data.approvedQuantity.toString()
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(Color.parseColor("#02B013"))

        }else if (data.status.equals("Rejected",true)){
            holder.approvedQuantityLayout.visibility = View.VISIBLE
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(Color.parseColor("#F71111"))

        }

        if (data.remarks != null){
            holder.remarks.text = data.remarks
        }else{
            holder.remarks.text = "-"
        }
    }

    override fun getItemCount(): Int {
        return customerBasicInfoListJson.size
    }
}