package com.loyaltyworks.keshavcement.ui.pendingClaimRequest.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowPendingClaimRequestBinding
import com.loyaltyworks.keshavcement.model.LstTransactionApprovalDetail
import com.loyaltyworks.keshavcement.utils.AppController
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick

class PendingClaimRequestAdapter(val lstTransactionApprovalDetails: List<LstTransactionApprovalDetail>,var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<PendingClaimRequestAdapter.ViewHolder>() {

    var buttonClicked : Boolean = false

    interface OnItemClickCallBack {
        fun onApproveClickResponse(itemView: View,position: Int, status: String, lstTransactionApprovalDetails: LstTransactionApprovalDetail)
        fun onRejectClickResponse(itemView: View,position: Int, status: String, lstTransactionApprovalDetails: LstTransactionApprovalDetail)
    }



    inner class ViewHolder(val binding: RowPendingClaimRequestBinding,val pos: Int): RecyclerView.ViewHolder(binding.root) {
        val date = binding.date
        val custImage = binding.custImage
        val custType = binding.custType
        val custName = binding.custName
        val location = binding.location
        val prodName = binding.prodName
        val remarks = binding.remarks
        val custMobile = binding.custMobile
        val invoiceNo = binding.invoiceNo

        val qtyMinus = binding.qtyMinus
        val qtyPlus = binding.qtyPlus
        val qtyTextview = binding.qtyTextview

        val approveBtn = binding.approveBtn
        val rejectBtn = binding.rejectBtn

        init {

            qtyTextview.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (qtyTextview.hasFocus()){
                        if (!buttonClicked){
                            if (p0.toString().isEmpty())
                                return

                            if (p0.toString() == "0") {
                                qtyTextview.text.clear()
                                Toast.makeText(itemView.context, "Quantity should be grater than 0", Toast.LENGTH_SHORT).show()
                                return
                            }

                            if (p0.toString().toInt() <= lstTransactionApprovalDetails[pos].quantity!!){
                                lstTransactionApprovalDetails[pos].updatedQuantity = p0.toString().toInt()
                            }else{
                                qtyTextview.text.clear()
                                Toast.makeText(itemView.context, "Max Quantity should not more than " + lstTransactionApprovalDetails[pos].quantity, Toast.LENGTH_SHORT).show()
                                return
                            }
                            Log.d("ebfhefh", "text : " + p0.toString())
                            Log.d("ebfhefh", "qty : " + lstTransactionApprovalDetails[pos].quantity)

                        }

                    }

                }
            })

            remarks.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                    if (remarks.hasFocus()){
                        lstTransactionApprovalDetails[pos].enteredRemarks = p0.toString()
//                    }
                }
                override fun afterTextChanged(p0: Editable?) {}

            })

            qtyPlus.setOnClickListener {
                buttonClicked = true
                if (!qtyTextview.text.isEmpty()){
                    if (qtyTextview.text.toString().toInt() < lstTransactionApprovalDetails[pos].quantity!!){
                        lstTransactionApprovalDetails[pos].updatedQuantity = lstTransactionApprovalDetails[pos].updatedQuantity + 1
                        qtyTextview.setText(lstTransactionApprovalDetails[pos].updatedQuantity.toString())
                        buttonClicked = false
                    }else{
                        buttonClicked = false
                        Toast.makeText(itemView.context, "Max Quantity should be " + lstTransactionApprovalDetails[pos].quantity, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    qtyTextview.setText("1")
                    lstTransactionApprovalDetails[pos].updatedQuantity = 1
                    buttonClicked = false
                }

            }


            qtyMinus.setOnClickListener {
                if (lstTransactionApprovalDetails[pos].updatedQuantity > 1){
                    lstTransactionApprovalDetails[pos].updatedQuantity = lstTransactionApprovalDetails[pos].updatedQuantity - 1
                    qtyTextview.setText(lstTransactionApprovalDetails[pos].updatedQuantity.toString())
                }
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPendingClaimRequestBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding,viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = lstTransactionApprovalDetails[position]

        data.updatedQuantity = data.quantity!!
        holder.remarks.setText("")

        if (!data.productImage.isNullOrEmpty()){
            Glide.with(holder.itemView.context).asBitmap()
                .error(R.drawable.ic_default_img)
                .load(BuildConfig.PROMO_IMAGE_BASE + data.productImage.toString().split("~")[1])
                .into(holder.custImage)
        }


        if (!data.tranDate.isNullOrEmpty()){
            holder.date.text = AppController.dateAPIFormat(data.tranDate.toString().split(" ")[0])
        }else{
            holder.date.text = "DD/MM/YYYY"
        }

        holder.location.text = data.locationName
        holder.custType.text = data.memberType
        holder.custName.text = data.memberName
        holder.prodName.text = data.prodName
        holder.custMobile.text = data.mobile
        holder.invoiceNo.text = data.invoiceNo
        holder.qtyTextview.setText(data.quantity.toString())


        holder.approveBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            if (!holder.qtyTextview.text.toString().isNullOrEmpty()){
                onItemClickListener.onApproveClickResponse(v,position,"1",data)
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.please_enter_quantity), Toast.LENGTH_SHORT).show()
            }


        }

        holder.rejectBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            if (!holder.qtyTextview.text.toString().isNullOrEmpty()){
                if (!data.enteredRemarks.isNullOrEmpty()){
                    onItemClickListener.onRejectClickResponse(v,position,"-1",data)
                }else{
                    Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.enter_remarks), Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.enter_quantity), Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return lstTransactionApprovalDetails.size
    }
}