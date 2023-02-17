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


     class ViewHolder(binding: RowMyRedemptionsBinding, position: Int) :
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
        holder.quantity.text = rewardTransDetails.quantity.toString()
        holder.customerName.text = rewardTransDetails.fullName.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .thumbnail(0.1f)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + rewardTransDetails.productImage.toString())
            .into(holder.prodImg)

        if (rewardTransDetails.status!! == 0) {
            holder.status.text = "Pending"
            holder.status.setBackgroundResource(R.drawable.pending_bg)
//                status.setTextColor(Color.parseColor("#ADC000"))
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))

        } else if (rewardTransDetails.status == 2) {
            holder.status.text = "Processed"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 3) {
            holder.status.text = "Cancelled"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        } else if (rewardTransDetails.status == 4) {
            holder.status.text = "Delivered"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 7) {
            holder.status.text = "Returned"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        } else if (rewardTransDetails.status == 8) {
            holder.status.text = "Redispatched"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        } else if (rewardTransDetails.status == 9) {
            holder.status.text = "OnHold"
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))
        } else if (rewardTransDetails.status == 10) {
            holder.status.text = "Dispatched"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 11) {
            holder.status.text = "Out for Delivery"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 12) {
            holder.status.text = "Address Verified"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 13) {
            holder.status.text = "Posted for approval"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 14) {
            holder.status.text = "Vendor Alloted"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 15) {
            holder.status.text = "Vendor Rejected"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        } else if (rewardTransDetails.status == 16) {
            holder.status.text = "Posted for approval 2"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 17) {
            holder.status.text = "Cancel Request"
            holder.status.setBackgroundResource(R.drawable.rejected_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.red))

        } else if (rewardTransDetails.status == 18) {
            holder.status.text = "Redemption Verified"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 19) {
            holder.status.text = "Delivery Confirmed"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 20) {
            holder.status.text = "Return Requested"
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))

        } else if (rewardTransDetails.status == 21) {
            holder.status.text = "Return Pickup Schedule"
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))

        } else if (rewardTransDetails.status == 22) {
            holder.status.text = "Picked Up"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else if (rewardTransDetails.status == 23) {
            holder.status.text = "Return Received"
            holder.status.setBackgroundResource(R.drawable.pending_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color63))

        } else if (rewardTransDetails.status == 24) {
            holder.status.text = "In Transit"
            holder.status.setBackgroundResource(R.drawable.approved_bg)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.color17))

        } else {
            holder.status.text = "-"
            holder.status.setBackgroundResource(R.drawable.yellow_corner)
            holder.status.setTextColor(holder.itemView.context.resources.getColor(R.color.black))
        }

        /* Gift Type */
        Log.d("hgdf","hghb " + rewardTransDetails.redemptionType.toString())
        if (rewardTransDetails.redemptionType!! == 1) {
            holder.catalogueType.text =  "Catalogue"
        }else if (rewardTransDetails.redemptionType!! == 0) {
            holder.catalogueType.text =  "Catalogue"
        }else if (rewardTransDetails.redemptionType!! == 2) {
            holder.catalogueType.text =  "Inventory"
        }else if (rewardTransDetails.redemptionType!! == 3) {
            holder.catalogueType.text =  "Dream Gift"
//            holder.rowDetails.visibility = View.GONE
        }else if (rewardTransDetails.redemptionType!! == 4) {
            holder.catalogueType.text =  "Voucher"
//            holder.rowDetails.visibility = View.GONE
        }else if (rewardTransDetails.redemptionType!! == 5) {
            holder.catalogueType.text =  "Bank Transfer"
//            holder.rowDetails.visibility = View.GONE
        }


        if (!rewardTransDetails.redemptionType!!.equals(3) && !rewardTransDetails.redemptionType!!.equals(4)) {
            if (rewardTransDetails.categoryName.toString() != "null")
                holder.category.text = " / " + rewardTransDetails.categoryName.toString()
            else
                holder.category.text = " / "
        } else {
            holder.category.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return objCatalogueRedemReqList.size
    }
}
