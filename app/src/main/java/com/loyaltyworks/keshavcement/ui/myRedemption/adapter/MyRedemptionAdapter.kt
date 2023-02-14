package com.loyaltyworks.keshavcement.ui.myRedemption.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowMyRedemptionsBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick


class MyRedemptionAdapter(val objCatalogueRedemReqList: List<ObjCatalogueRedemReq>) : RecyclerView.Adapter<MyRedemptionAdapter.ViewHolder>() {


    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class ViewHolder(binding: RowMyRedemptionsBinding, position: Int) :
        RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val customerName = binding.customerName
        val status = binding.status
        val prodImg = binding.prodImg
        val catalogueType = binding.catalogueType
        val category = binding.category
        val productName = binding.productName
        val points = binding.points
        val quantity = binding.quantity


      /*  init {
//            prodName.text =  myRedemptionResponse.objCatalogueRedemReqList!![adapterPosition].productName
            val data = objCatalogueRedemReqList[position]

            //     Log.d("dghfsdhf", data!!)

            if (data.status!! == 0) {
                status.text = "Pending"
                status.setBackgroundResource(R.drawable.dashed_yellow_bg)
                status.setTextColor(Color.parseColor("#ADC000"))

            } else if (data.status == 2) {
                status.text = "Processed"
                status.setBackgroundResource(R.drawable.dashed_green_bg)

            } else if (data.status == 3) {
                status.text = "Cancelled"
                status.setBackgroundResource(R.drawable.dashed_pink_bg)

            } else if (data.status == 4) {
                status.text = "Delivered"
                status.setBackgroundResource(R.drawable.dashed_green_bg)
                status.setTextColor(Color.parseColor("#3CCC00"))

            } else if (data.status == 7) {
                status.text = "Returned"
                status.setBackgroundResource(R.drawable.dashed_pink_bg)

            } else if (data.status == 8) {
                status.text = "Redispatched"
                status.setBackgroundResource(R.drawable.dashed_pink_bg)

            } else if (data.status == 9) {
                status.text = "OnHold"
                status.setBackgroundResource(R.drawable.dashed_yellow_bg)
                status.setTextColor(Color.parseColor("#ADC000"))
            } else if (data.status == 10) {
                status.text = "Dispatched"
                status.setBackgroundResource(R.drawable.dashed_green_bg)

            } else if (data.status == 11) {
                status.text = "Out for Delivery"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 12) {
                status.text = "Address Verified"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 13) {
                status.text = "Posted for approval"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 14) {
                status.text = "Vendor Alloted"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 15) {
                status.text = "Vendor Rejected"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 16) {
                status.text = "Posted for approval 2"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 17) {
                status.text = "Cancel Request"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 18) {
                status.text = "Redemption Verified"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 19) {
                status.text = "Delivery Confirmed"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 20) {
                status.text = "Return Requested"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 21) {
                status.text = "Return Pickup Schedule"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 22) {
                status.text = "Picked Up"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 23) {
                status.text = "Return Received"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else if (data.status == 24) {
                status.text = "In Transit"
                status.setBackgroundResource(R.drawable.dashed_green_bg2)

            } else {
                status.text = "-"
                status.setBackgroundResource(R.drawable.yellow_corner)
            }

        }*/

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RowMyRedemptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rewardTransDetails = objCatalogueRedemReqList[position]

        holder.productName.text = rewardTransDetails.productName.toString()

        holder.date.text = AppController.dateAPIFormat(rewardTransDetails.jRedemptionDate.toString().split(" ")[0])
        holder.points.text = rewardTransDetails.redemptionPoints.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .thumbnail(0.1f)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + rewardTransDetails.productImage.toString())
            .into(holder.prodImg)

        /* Gift Type */
       /* Log.d("hgdf", "hghb " + rewardTransDetails.redemptionType.toString())
        if (rewardTransDetails.redemptionType!! == 1) {
            holder.giftType.text = "Catalogue"
        } else if (rewardTransDetails.redemptionType!! == 0) {
            holder.giftType.text = "Catalogue"
        } else if (rewardTransDetails.redemptionType!! == 2) {
            holder.giftType.text = "Inventory"
        } else if (rewardTransDetails.redemptionType!! == 3) {
            holder.giftType.text = "Dream Gift"
//            holder.rowDetails.visibility = View.GONE
        } else if (rewardTransDetails.redemptionType!! == 4) {
            holder.giftType.text = "Voucher"
//            holder.rowDetails.visibility = View.GONE
        } else if (rewardTransDetails.redemptionType!! == 5) {
            holder.giftType.text = "Bank Transfer"
//            holder.rowDetails.visibility = View.GONE
        }*/

        if (!rewardTransDetails.redemptionType!!.equals(3) && !rewardTransDetails.redemptionType!!.equals(4)) {
            if (rewardTransDetails.categoryName.toString() != "null")
                holder.category.text = "Category : " + rewardTransDetails.categoryName.toString()
            else
                holder.category.text = "Category : -"
        } else {
            holder.category.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return objCatalogueRedemReqList.size
    }
}
