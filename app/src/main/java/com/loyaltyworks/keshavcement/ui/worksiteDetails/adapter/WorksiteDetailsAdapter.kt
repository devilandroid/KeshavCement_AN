package com.loyaltyworks.keshavcement.ui.worksiteDetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowWorksiteDetailsBinding
import com.loyaltyworks.keshavcement.model.LstWorkSiteInfo

class WorksiteDetailsAdapter(val lstWorkSiteInfo: List<LstWorkSiteInfo>) : RecyclerView.Adapter<WorksiteDetailsAdapter.ViewHolder>() {



    class ViewHolder(val binding: RowWorksiteDetailsBinding): RecyclerView.ViewHolder(binding.root) {

        val date = binding.date
        val locationName = binding.locationName
        val status = binding.status
        val ownerName = binding.ownerName
        val ownerMobile = binding.ownerMobile
        val engineerName = binding.engineerName
        val engineerMobile = binding.engineerMobile
        val workLevel = binding.workLevel
        val tentativeDate = binding.tentativeDate
        val remarks = binding.remarks
        val ownerAddress = binding.ownerAddress

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowWorksiteDetailsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstWorkSiteInfo[position]

        holder.date.text = data.createdDate
        holder.locationName.text = data.siteName
        holder.ownerName.text = data.contactPersonName
        holder.ownerMobile.text = data.contactNumber
        holder.ownerAddress.text = data.address
        holder.engineerName.text = data.contactPersonName1
        holder.engineerMobile.text = data.contactNumber1
        holder.workLevel.text = "- " + data.worklevel
        holder.tentativeDate.text = "- " + data.tentativeDate
        holder.remarks.text = "- " + data.remarks



        if (data.verificationStatus == "Pending"){
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.pending_yellow1))
            holder.status.text = "Pending"
        }else if (data.verificationStatus == "Approved"){
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.green))
            holder.status.text = "Approved"
        }else if (data.verificationStatus == "Rejected"){
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))
            holder.status.text = "Rejected"
        }



    }

    override fun getItemCount(): Int {
        return lstWorkSiteInfo.size
    }

}
