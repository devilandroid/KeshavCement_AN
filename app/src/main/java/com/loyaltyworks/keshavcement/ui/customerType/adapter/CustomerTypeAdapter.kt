package com.loyaltyworks.keshavcement.ui.customerType.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCustomerTypeBinding
import com.loyaltyworks.keshavcement.model.LstAttributesDetail
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class CustomerTypeAdapter(val lstAttributesDetails: List<LstAttributesDetail>, var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CustomerTypeAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onCustomerTypeListClickResponse(itemView: View, lstAttributesDetails: LstAttributesDetail)
    }

    class ViewHolder(val binding: RowCustomerTypeBinding): RecyclerView.ViewHolder(binding.root) {
        val customerTypeName = binding.customerTypeName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCustomerTypeBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstAttributesDetails[position]

        if (data.attributeId == BuildConfig.Engineer){
            holder.customerTypeName.text = holder.itemView.context.getString(R.string.engineer)

        }else if (data.attributeId == BuildConfig.Mason){
            holder.customerTypeName.text = holder.itemView.context.getString(R.string.mason)

        }else if (data.attributeId == BuildConfig.Dealer){
            holder.customerTypeName.text = holder.itemView.context.getString(R.string.dealer)

        }else if (data.attributeId == BuildConfig.SubDealer){
            holder.customerTypeName.text = holder.itemView.context.getString(R.string.sub_dealer)

        }else if (data.attributeId == BuildConfig.SupportExecutive){
            holder.customerTypeName.text = holder.itemView.context.getString(R.string.support_executive)
        }


        holder.itemView.setOnClickListener { v ->
            if (BlockMultipleClick.click()) return@setOnClickListener

            onItemClickListener.onCustomerTypeListClickResponse(v,data)
        }
    }

    override fun getItemCount(): Int {
        return lstAttributesDetails.size
    }
}