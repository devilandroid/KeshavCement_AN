package com.loyaltyworks.keshavcement.ui.cashTransferApproval.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCashTransferApprovalBinding
import com.loyaltyworks.keshavcement.model.LstCustomerCashTransferedDetail
import com.loyaltyworks.keshavcement.model.ObjCatalogueRedemReq
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class CashTransferApprovalAdapter(val lstCustomerCashTransferedDetails: List<LstCustomerCashTransferedDetail>, var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<CashTransferApprovalAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onApproveClickResponse(itemView: View, position: Int, status: String, lstCustomerCashTransferedDetails: LstCustomerCashTransferedDetail)
        fun onRejectClickResponse(itemView: View, position: Int, status: String, lstCustomerCashTransferedDetails: LstCustomerCashTransferedDetail)
    }


    inner class ViewHolder(val binding: RowCashTransferApprovalBinding,val pos: Int): RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val custImage = binding.custImage
        val custType = binding.custType
        val custName = binding.custName
        val location = binding.location
        val value = binding.value
        val points = binding.points
        val remarks = binding.remarks
        val custMobile = binding.custMobile

        val rejectBtn = binding.rejectBtn
        val approveBtn = binding.approveBtn

        init {

            remarks.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    if (remarks.hasFocus()){
                    lstCustomerCashTransferedDetails[pos].enteredRemarks = p0.toString()
//                    }
                }
                override fun afterTextChanged(p0: Editable?) {}

            })

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCashTransferApprovalBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding,viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstCustomerCashTransferedDetails[position]

        holder.remarks.setText("")

        if (!data.dispalyImage.isNullOrEmpty()){
            Glide.with(holder.itemView.context).asBitmap()
                .error(R.drawable.ic_default_img)
                .load(BuildConfig.PROMO_IMAGE_BASE + data.dispalyImage.toString().split("~")[1])
                .into(holder.custImage)

        }


        if (!data.createdDate.isNullOrEmpty()){
            holder.date.text = AppController.dateAPIFormat(data.createdDate.toString().split(" ")[0])
        }else{
            holder.date.text = "DD/MM/YYYY"
        }

        holder.location.text = data.locationName
        holder.custType.text = data.customerType
        holder.custName.text = data.customerName
        holder.value.text = data.transferedPointsinAmount
        holder.custMobile.text = data.customerMobile
        holder.points.text = data.points.toString()


        holder.approveBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            onItemClickListener.onApproveClickResponse(v,position,"1",data)
        }

        holder.rejectBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            if (!data.enteredRemarks.isNullOrEmpty()){
                onItemClickListener.onRejectClickResponse(v,position,"-1",data)
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.enter_remarks), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return lstCustomerCashTransferedDetails.size
    }
}