package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowProductBinding
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class ProductAdapter(var onItemClickListener: OnItemClickCallBack) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onProductListItemClickResponse(itemView: View, position: Int, /*objCatalogue: ObjCatalogue*/)
//        fun onAddPlannerListener(catalogueId: Int)
//        fun onAddToCart(catalogueId: Int)
//        fun onHoldAddtoCart()
//        fun onProductImageClicked(position: Int,objCatalogueList: List<ObjCatalogue>)

    }

    class ViewHolder(val binding: RowProductBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.setOnClickListener { v ->
            if (BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onProductListItemClickResponse(v, position,/*objCatalogue*/)
        }
    }

    override fun getItemCount(): Int {
        return 20
    }

}
