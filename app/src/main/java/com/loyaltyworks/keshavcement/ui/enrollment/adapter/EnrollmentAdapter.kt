package com.loyaltyworks.keshavcement.ui.enrollment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowEnrollmentBinding
import com.loyaltyworks.keshavcement.model.LstCustParentChildMappingCustList

class EnrollmentAdapter(val lstCustParentChildMapping: List<LstCustParentChildMappingCustList>): RecyclerView.Adapter<EnrollmentAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowEnrollmentBinding): RecyclerView.ViewHolder(binding.root) {
        val custImage = binding.custImage
        val custType = binding.custType
        val points = binding.points
        val custName = binding.custName
        val mobileNo = binding.mobileNo
        val memId = binding.memId
        val lastPurchaseDate = binding.lastPurchaseDate
        val lastRedemptionDate = binding.lastRedemptionDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowEnrollmentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstCustParentChildMapping[position]

        holder.custType.text = data.customerType
        holder.points.text = data.totalPointsBalance.toString()
        holder.custName.text = data.firstName
        holder.mobileNo.text = data.mobile
        holder.memId.text = data.loyaltyID
        holder.lastPurchaseDate.text = data.lastPurchaseDate
        holder.lastRedemptionDate.text = data.lastRedemptionDate

        if (data.customerImage.isNullOrEmpty()){
            Glide.with(holder.itemView.context).asBitmap()
                .error(R.drawable.ic_default_img)
                .thumbnail(0.1f)
                .load(BuildConfig.CATALOGUE_IMAGE_BASE + data.customerImage.toString())
                .into(holder.custImage)
        }

    }

    override fun getItemCount(): Int {
        return lstCustParentChildMapping.size
    }
}