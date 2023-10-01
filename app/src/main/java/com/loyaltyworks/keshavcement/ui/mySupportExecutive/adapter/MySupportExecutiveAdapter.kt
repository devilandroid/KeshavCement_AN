package com.loyaltyworks.keshavcement.ui.mySupportExecutive.adapter

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowMySupportExecutiveBinding
import com.loyaltyworks.keshavcement.model.LstCustParentChildMapping
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class MySupportExecutiveAdapter(val lstCustParentChildMapping: List<LstCustParentChildMapping>,var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<MySupportExecutiveAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onActivateDeactivateClickResponse(itemView: View,status: String, lstCustParentChildMapping: LstCustParentChildMapping, )
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder(val binding: RowMySupportExecutiveBinding): RecyclerView.ViewHolder(binding.root) {
        val custImage = binding.custImage
        val custName = binding.custName
        val mobileNumber = binding.mobileNumber
        val memId = binding.memId
        val statusImg = binding.statusImg
        val statusName = binding.statusName

        val activeDeactiveLayout = binding.activeDeactiveLayout
        val activeDeactiveBtn = binding.activeDeactiveBtn

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowMySupportExecutiveBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstCustParentChildMapping[position]

        holder.custName.text = data.firstName
        holder.mobileNumber.text = data.mobile
        holder.memId.text = data.loyaltyID

        if (!data.customerImage.isNullOrEmpty()){
            Glide.with(holder.itemView.context).asBitmap()
                .error(R.drawable.ic_default_img)
                .thumbnail(0.1f)
                .load(BuildConfig.PROMO_IMAGE_BASE + data.customerImage.toString().split("~")[1])
                .into(holder.custImage)
        }


        if (data.isActive == 1){
            holder.statusName.text = "Active"
            holder.statusImg.setImageResource(R.drawable.ic_active)

            holder.activeDeactiveBtn.text = "Deactivate"
            holder.activeDeactiveBtn.setBackgroundResource(R.drawable.deactivate_bg)
            holder.activeDeactiveBtn.setTextColor(Color.parseColor("#EA1616"))
        }else{
            holder.statusName.text = "In-Active"
            holder.statusImg.setImageResource(R.drawable.in_active)

            holder.activeDeactiveBtn.text = "Activate"
            holder.activeDeactiveBtn.setBackgroundResource(R.drawable.activate_bg)
            holder.activeDeactiveBtn.setTextColor(Color.parseColor("#00BE06"))
        }


        holder.activeDeactiveBtn.setOnClickListener { v ->
            if (BlockMultipleClick.click()) return@setOnClickListener

            if (data.isActive == 1){
                /*** For Deactivate ***/
                onItemClickListener.onActivateDeactivateClickResponse(v,"false", data)
            }else{
                /*** For Activate ***/
                onItemClickListener.onActivateDeactivateClickResponse(v,"true", data)
            }

        }

        holder.mobileNumber.setOnClickListener { v ->
            holder.itemView.context.startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.fromParts("tel", data.mobile, null)
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return  lstCustParentChildMapping.size
    }
}