package com.loyaltyworks.keshavcement.ui.myRedemption.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowRedemptionTrackerBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueee
import com.loyaltyworks.keshavcement.utils.AppController

class RedemptionTrackerAdapter(val objCatalogueRedemReqList: List<ObjCatalogueee> ): RecyclerView.Adapter<RedemptionTrackerAdapter.ViewHolder>(){
    class ViewHolder(binding: RowRedemptionTrackerBinding):RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val status = binding.status
        val line = binding.line
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowRedemptionTrackerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val redemStatus = objCatalogueRedemReqList[position]

//        holder.date.text =  (redemStatus.createdDate.toString().split(" ")[0])
        holder.date.text =  AppController.dateAPIFormat(redemStatus.createdDate.toString().split(" ")[0])

        if (position == objCatalogueRedemReqList.size - 1){
            holder.line.visibility = View.GONE
        }

        /* Status */
        if (redemStatus.status!! == 0){
            holder.status.text =  "Pending"
        }else if (redemStatus.status == 2){
            holder.status.text = "Processed"
        }else if (redemStatus.status == 3){
            holder.status.text = "Cancelled"
        }else if (redemStatus.status == 4){
            holder.status.text =  "Delivered"
        }else if (redemStatus.status == 7){
            holder.status.text =  "Returned"
        }else if (redemStatus.status == 8){
            holder.status.text =  "Redispatched"
        }else if (redemStatus.status == 9){
            holder.status.text =  "OnHold"
        }else if (redemStatus.status == 10){
            holder.status.text =  "Dispatched"
        }else if (redemStatus.status == 11){
            holder.status.text =  "Out for Delivery"
        }else if (redemStatus.status == 12){
            holder.status.text = "Address Verified"
        }else if (redemStatus.status == 13){
            holder.status.text = "Posted for approval"
        }else if (redemStatus.status == 14){
            holder.status.text = "Vendor Alloted"
        }else if (redemStatus.status == 15){
            holder.status.text = "Vendor Rejected"
        }else if (redemStatus.status == 16){
            holder.status.text = "Posted for approval 2"
        }else if (redemStatus.status == 17){
            holder.status.text = "Cancel Request"
        }else if (redemStatus.status == 18){
            holder.status.text = "Redemption Verified"
        }else if (redemStatus.status == 19){
            holder.status.text = "Delivery Confirmed"
        }else if (redemStatus.status == 20){
            holder.status.text = "Return Requested"
        }else if (redemStatus.status == 21){
            holder.status.text = "Return Pickup Schedule"
        }else if (redemStatus.status == 22){
            holder.status.text = "Picked Up"
        }else if (redemStatus.status == 23){
            holder.status.text = "Return Received"
        }else if (redemStatus.status == 24){
            holder.status.text = "In Transit"
        }

    }

    override fun getItemCount(): Int {
        return objCatalogueRedemReqList.size
    }
}