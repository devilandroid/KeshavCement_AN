package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCashTransferBinding
import com.loyaltyworks.keshavcement.model.ObjCataloguee
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper

class CashTransferAdapter(val objCatalogueList: List<ObjCataloguee>, var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CashTransferAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onListItemClickResponse(itemView: View, position: Int, objCatalogue: ObjCataloguee)
        fun onRedeemBtnClickResponse(itemView: View, position: Int, objCatalogue: ObjCataloguee)
        fun onHoldRedeemVoucher()
    }

    class ViewHolder(val binding: RowCashTransferBinding): RecyclerView.ViewHolder(binding.root) {
        val redeemBtn = binding.redeemBtn
        val productImg = binding.productImg
        val prodName = binding.prodName
        val desc = binding.desc
        val points = binding.points

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objCatalogue = objCatalogueList[position]

        if (objCatalogue.isRedeemable == 1){
            holder.redeemBtn.visibility = View.VISIBLE
            if (objCatalogue.pointsRequired!!.toInt() <= PreferenceHelper.getStringValue(holder.itemView.context, BuildConfig.RedeemablePointsBalance).toInt()) {
                holder.redeemBtn.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_dark)
            } else {
                holder.redeemBtn.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_grey)
            }
        }else{
            holder.redeemBtn.visibility = View.GONE
        }

        holder.prodName.text = objCatalogue.productName
        holder.points.text = objCatalogue.pointsRequired.toString()
        holder.desc.text =  objCatalogue.productDesc

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + objCatalogue.productImage)
            .into(holder.productImg)



        holder.redeemBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            if (objCatalogue.isRedeemable == 1){
                if (objCatalogue.pointsRequired!!.toInt() <= PreferenceHelper.getStringValue(holder.itemView.context,BuildConfig.RedeemablePointsBalance).toInt()) {
                    if (PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 1) {
                        onItemClickListener.onRedeemBtnClickResponse(v,position,objCatalogue)
                    }else{
                        onItemClickListener.onHoldRedeemVoucher()
                    }
                }else{
                    Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.insufficient_point_balance_to_redeem), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
            }

        }


        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onListItemClickResponse(v,position,objCatalogue)
        }
    }

    override fun getItemCount(): Int {
        return objCatalogueList.size
    }
}