package com.loyaltyworks.keshavcement.ui.support.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowQueryListingBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class SupportAdapter(var onItemClickListener: OnClickCallBack) : RecyclerView.Adapter<SupportAdapter.ViewHolder>() {

    interface OnClickCallBack{

        fun onQueryListItemClickResponse(itemView: View, position: Int, /*productList: List<ObjCustomerAllQueryJson?>?*/)
    }

    class ViewHolder(val binding: RowQueryListingBinding) : RecyclerView.ViewHolder(binding.root) {
        val row_query_ref_no = binding.rowQueryRefNo
        val row_query_date = binding.rowQueryDate
        val row_query_time = binding.rowQueryTime
        val row_query_text = binding.rowQueryText
        val row_query_status = binding.rowQueryStatus
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowQueryListingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener!!.onQueryListItemClickResponse(v,position,/*queryListingResponse*/)
        }

    }

    override fun getItemCount(): Int {
        return 10
    }

}
