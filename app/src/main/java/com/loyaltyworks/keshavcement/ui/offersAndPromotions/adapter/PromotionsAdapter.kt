package com.loyaltyworks.keshavcement.ui.offersAndPromotions.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowPromotionsBinding
import com.loyaltyworks.keshavcement.model.LstPromotionJson
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class PromotionsAdapter(var lstPromotionJson: List<LstPromotionJson>,var onItemClickListener:OnOfferDetailsCallback) : RecyclerView.Adapter<PromotionsAdapter.ViewHolder>() {

    interface OnOfferDetailsCallback{
        fun onOfferDetailsItemClickResponse(itemView: View, lstPromotionJson: LstPromotionJson)
    }

    class ViewHolder(val binding: RowPromotionsBinding): RecyclerView.ViewHolder(binding.root) {
        val promoDetails = binding.promoDetails
        val promoImg = binding.promoImage
        val title = binding.promoTitle
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPromotionsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = lstPromotionJson[position]

        holder.title.text = data.promotionName.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.PROMO_IMAGE_BASE + data.proImage)
            .into(holder.promoImg)

        holder.promoDetails.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onOfferDetailsItemClickResponse(v, data)
        }


    }

    override fun getItemCount(): Int {
        return lstPromotionJson.size
    }


}
