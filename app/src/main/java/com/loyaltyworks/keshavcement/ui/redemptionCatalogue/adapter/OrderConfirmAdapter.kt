package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowConfirmProductBinding
import com.loyaltyworks.keshavcement.model.CatalogueSaveCartDetailResponse

class OrderConfirmAdapter(val cartList : List<CatalogueSaveCartDetailResponse>): RecyclerView.Adapter<OrderConfirmAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowConfirmProductBinding): RecyclerView.ViewHolder(binding.root) {
        val prodImg = binding.productImg
        val prodName = binding.productName
        val points = binding.points
        val category = binding.categoryName
        val quantity = binding.quantity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowConfirmProductBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = cartList[position]

        holder.prodName.text = data.productName
        holder.points.text = data.pointsRequired.toString()
        holder.category.text = data.catogoryName.toString()
        holder.quantity.text = data.noOfQuantity.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + data.productImage)
            .into(holder.prodImg)

    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}