package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCustomerBinding
import com.loyaltyworks.keshavcement.model.LstCustParentChildMappingCustList
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class CustomerSelectionAdapter(val lstCustParentChildMapping: List<LstCustParentChildMappingCustList>,var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CustomerSelectionAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onCustListItemClickResponse(itemView: View, position: Int,selectedCustomer:LstCustParentChildMappingCustList)
    }

    class ViewHolder(val binding: RowCustomerBinding): RecyclerView.ViewHolder(binding.root) {
        val custImage = binding.custImage
        val custType = binding.custType
        val points = binding.points
        val custName = binding.custName
        val mobileNo = binding.mobileNo
        val memId = binding.memId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCustomerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = lstCustParentChildMapping[position]

        holder.custType.text = data.customerType
        holder.points.text = data.totalPointsBalance.toString()
        holder.custName.text = data.firstName
        holder.mobileNo.text = data.mobile
        holder.memId.text = data.loyaltyID

        if (!data.customerImage.isNullOrEmpty()){
            Glide.with(holder.itemView.context).asBitmap()
                .error(R.drawable.ic_default_img)
                .thumbnail(0.1f)
                .load(BuildConfig.PROMO_IMAGE_BASE + data.customerImage.toString().split("~")[1])
                .into(holder.custImage)
        }


        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onCustListItemClickResponse(v,position,data)
        }

        holder.mobileNo.setOnClickListener { v ->
            holder.itemView.context.startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", data.mobile, null)
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return lstCustParentChildMapping.size
    }
}