package com.loyaltyworks.keshavcement.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowProductViewBinding
import com.loyaltyworks.keshavcement.model.LstProductDetailsProdView

class ProductViewAdapter(val lstProductListDetails: List<LstProductDetailsProdView>):RecyclerView.Adapter<ProductViewAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowProductViewBinding): RecyclerView.ViewHolder(binding.root) {
        val prodName = binding.prodName
        val quantity = binding.quantity
        val rowLayout = binding.rowLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProductViewBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstProductListDetails[position]

        if (position == lstProductListDetails.size - 1){
            holder.rowLayout.setBackgroundResource(R.drawable.stroke_bg6)
        }else{
            holder.rowLayout.setBackgroundResource(R.drawable.stroke_bg7)
        }

        holder.prodName.text = data.productName
        holder.quantity.text = data.quantity

    }

    override fun getItemCount(): Int {
        return lstProductListDetails.size
    }
}