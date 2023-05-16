package com.loyaltyworks.keshavcement.ui.myEarning.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowMyEarningsBinding
import com.loyaltyworks.keshavcement.model.LstRewardTransJsonDetail
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.PreferenceHelper

class MyEarningAdapter(var lstRewardTransJsonDetail: List<LstRewardTransJsonDetail>) : RecyclerView.Adapter<MyEarningAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowMyEarningsBinding): RecyclerView.ViewHolder(binding.root) {

        val date = binding.date
        val customerType = binding.customerType
        val custName = binding.custName
        val time = binding.time
        val prodName = binding.prodName
        val creditedPoints = binding.creditedPoints
        val remarks = binding.remarks
        val prodLayout = binding.prodLayout

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowMyEarningsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstRewardTransJsonDetail[position]

        holder.date.text =  AppController.dateAPIFormat(data.jTranDate.toString().split(" ")[0])
        holder.creditedPoints.text = data.rewardPoints!!.toInt().toString()


        if (data.loyaltyId!!.contains("~")){
            holder.customerType.text = data.loyaltyId!!.split("~")[2]
            holder.custName.text = data.loyaltyId!!.split("~")[1]
        }else{
            holder.customerType.text = PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].customerType
            holder.custName.text = PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].firstName
        }

        if (data.transactionType == "Cash Transfer"){
            holder.time.visibility = View.GONE
            holder.prodLayout.visibility = View.GONE

        }else{
            holder.time.visibility = View.GONE
            holder.prodLayout.visibility = View.VISIBLE

//            holder.time.text = data.jTranDate.toString().split(" ")[1]
            holder.prodName.text = data.prodName
        }


        if (data.transactionType == "BONUS"){
            holder.remarks.text = data.bonusName.toString()
        }else if (data.transactionType == "Referral"){
            holder.remarks.text =  "Referral Complimentary"

        }else if (data.transactionType == "Enrollment Complimentary"){
            holder.remarks.text =  "Enrollment Complimentary"

        }else if (data.transactionType == "WORKSITE"){
            holder.remarks.text =  "Worksite Program"

        }else if (data.transactionType == "Cash Transfer"){
            holder.remarks.text =  "Cash Transfer"

        }else{
            if (data.invoiceNo.toString() == "--"){
                holder.remarks.text =  "Reward Adjustment Credit"
            }else{
                holder.remarks.text =  data.remarks.toString()
            }

        }
    }

    override fun getItemCount(): Int {
        return  lstRewardTransJsonDetail.size
    }

}
