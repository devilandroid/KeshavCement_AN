package com.loyaltyworks.keshavcement.ui.dreamGift.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowDreamGiftBinding
import com.loyaltyworks.keshavcement.model.LstDreamGift
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import kotlin.math.roundToInt

class DreamGiftAdapter(var lstDreamGift: List<LstDreamGift>, var onItemClickListener: OnGiftItemCallBack):RecyclerView.Adapter<DreamGiftAdapter.ViewHolder>() {

    interface OnGiftItemCallBack {
        fun onGiftDetailClickResponse(itemView: View, lstDreamGift: LstDreamGift)
        fun onGiftRemoveClickResponse(itemView: View, lstDreamGift: LstDreamGift)
        fun onRedeemCallback(lstDreamGift: LstDreamGift)
        fun onHoldAddtoCart()
    }

    class ViewHolder(val binding: RowDreamGiftBinding): RecyclerView.ViewHolder(binding.root) {
        val giftName = binding.giftNameTv
        val pointsReq = binding.pointsReqTv
        val createDate = binding.createdDate
        val desiredDate = binding.desiredDate
        var progressBar = binding.seekbar
        val redeemBtn = binding.details
        val removeBtn = binding.removeBtn
        val percentage = binding.percentage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowDreamGiftBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstDreamGift[position]

        holder.giftName.text = data.dreamGiftName.toString()
        holder.pointsReq.text =  data.pointsRequired.toString()

        if(!data.jCreatedDate.isNullOrEmpty()){
            holder.createDate.text = AppController.dateAPIFormat(data.jCreatedDate.toString().split(" ")[0])
        }else{
            holder.createDate.text = "-"
        }

        if(!data.jDesiredDate.isNullOrEmpty()){
            holder.desiredDate.text = AppController.dateAPIFormat(data.jDesiredDate.toString().split(" ")[0])
        }else{
            holder.desiredDate.text = "-"
        }

        val points: Int = data.pointsRequired!!.toInt() /*+ data.tdsPoints!!.toDouble().toInt()*/
        val currentPoint: Int = PreferenceHelper.getDashboardDetails(holder.itemView.context)?.objCustomerDashboardList!![0].redeemablePointsBalance!!.toInt()    /*data.pointsBalance!!.toInt()*/

        holder.progressBar.max = points.toFloat()

        if (currentPoint == 0){
            holder.redeemBtn.visibility = View.GONE
        }else{
            if (data.isRedeemable == 1){
                holder.redeemBtn.visibility = View.VISIBLE
            }else{
                holder.redeemBtn.visibility = View.GONE
            }
        }

        val percentage = (currentPoint.toFloat() / points * 100).roundToInt()

        if ( currentPoint >= points){
            holder.progressBar.setProgress(points.toFloat())
            holder.redeemBtn.isEnabled = true
            holder.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_dark)
            holder.percentage.text = "100%"

        }else{

            holder.progressBar.setProgress(currentPoint.toFloat())
            holder.redeemBtn.isEnabled = false
            holder.redeemBtn.setBackgroundResource(R.drawable.product_corner_bg_grey)
            holder.percentage.text = percentage.toString() +"%"
        }

        holder.itemView.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onGiftDetailClickResponse(v, data)
        }


        holder.redeemBtn.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener
            if (data.is_DreamGiftRedeemable == 1){
                if (PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 6 ||
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 0 ||
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 2 ||
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 5 ||
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 3 ||
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 4){

                    onItemClickListener.onHoldAddtoCart()
                }else{
                    onItemClickListener.onRedeemCallback(data)
                }
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.not_allowed_to_redeem_contact_administrator), Toast.LENGTH_SHORT).show()
            }
        }

        holder.removeBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onGiftRemoveClickResponse(v,data)
        }



    }

    override fun getItemCount(): Int {
        return lstDreamGift.size
    }
}