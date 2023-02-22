package com.loyaltyworks.keshavcement.ui.wishlist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowWishlistBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogues
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class WishlistAdapter(var objCatalogues: List<ObjCatalogues>, var onItemClickListener: OnPlannerCallBack): RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {
    var productExistCart: String = "0"

    interface OnPlannerCallBack {
        fun onPlannerDetailsClickResponse(itemView: View, objCatalogues: ObjCatalogues)
        fun onPlannerRemoveClickResponse(itemView: View, objCatalogues: ObjCatalogues)
        fun onPlannerRedeemClickResponse(itemView: View, objCatalogues: ObjCatalogues)
    }

    class ViewHolder(binding: RowWishlistBinding): RecyclerView.ViewHolder(binding.root) {
        val prodName = binding.prodName
        val points = binding.prodPnts
        val redeemDate = binding.redemDate
        val prodImg = binding.prodImage

        val cancelBtn = binding.cancelBtn
        val redeemBtn = binding.redeem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowWishlistBinding.inflate( LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = objCatalogues[position]

        if (data.isRedeemable == 1){
            holder.redeemBtn.visibility = View.VISIBLE
            if (data.pointReqToAcheiveProduct!! <= 0 && data.redeemablePointBalance!! >= data.pointsRequired!!){

                holder.redeemBtn.isEnabled = true
                holder.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
            }else{
                holder.redeemBtn.isEnabled = false
                holder.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
            }
        }else{
            holder.redeemBtn.visibility = View.GONE
        }


        holder.prodName.text = data.productName
        holder.redeemDate.text = data.actualRedemptionDate.toString()
        holder.points.text = data.pointsRequired.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + data.productImage)
            .into(holder.prodImg)

        holder.cancelBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onPlannerRemoveClickResponse(v,data)
        }

        holder.redeemBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            productExistCart= data.productCode.toString().split("~")[1]
            if (productExistCart == "0"){
                productExistCart = "1"
                onItemClickListener.onPlannerRedeemClickResponse(v, data)
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.already_added_in_cart), Toast.LENGTH_SHORT).show()
            }

        }

        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onPlannerDetailsClickResponse(v, data)
        }


    }

    override fun getItemCount(): Int {
        return objCatalogues.size
    }
}