package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowVoucherBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueFixedPoint
import com.loyaltyworks.keshavcement.model.ObjCatalogueListtt
import com.loyaltyworks.keshavcement.model.RedeemGiftResponse
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import java.util.ArrayList

class VoucherAdapter(var redeemGiftResponse: RedeemGiftResponse, var onItemClickListener: voucherListAdpaterCallback): RecyclerView.Adapter<VoucherAdapter.ViewHolder>() {

    var defaultData = arrayOf("0")
    var objCatalogueFixedPoint: ObjCatalogueFixedPoint? = null
    var amount = "0"

    interface voucherListAdpaterCallback {
        fun onDetailVoucherFromAdapter(itemView: View, position: Int,CatalogueVouchers: ObjCatalogueListtt)
        fun onRedeemVoucherFromAdapter(itemView: View, position: Int,CatalogueVouchers: ObjCatalogueListtt, amount: String)
    }

    class ViewHolder(val binding: RowVoucherBinding): RecyclerView.ViewHolder(binding.root) {
        val redeem = binding.redeemBtn

        val amount = binding.amount
        val mSpinnerHost = binding.mngPriceSpinner
        val voucher_range = binding.voucherRange
        val voucher_name = binding.voucherName
        val voucher_image = binding.voucherImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowVoucherBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val redeemGiftResponses = redeemGiftResponse.objCatalogueList!![position]

        holder.voucher_name.text = redeemGiftResponses.productName

        if(redeemGiftResponses.product_type == 1) {

            holder.voucher_range.text = "INR ${redeemGiftResponses.min_points.toString()} - ${redeemGiftResponses.max_points.toString()}"
            holder.amount.visibility = View.VISIBLE
            holder.mSpinnerHost.visibility = View.GONE
            holder.mSpinnerHost.adapter = ArrayAdapter(
                holder.itemView.context,
                android.R.layout.simple_spinner_item,
                defaultData
            )

            holder.redeem.visibility = View.VISIBLE

        }else{

            holder.voucher_range.visibility = View.INVISIBLE
            holder.amount.setText("")
            holder.amount.visibility = View.GONE

            if (!redeemGiftResponse.objCatalogueFixedPoints.isNullOrEmpty()) {

                holder.mSpinnerHost.adapter =
                    VoucherPointsAdapter(
                        holder.itemView.context,/*redeemGiftResponses.ObjCatalogueFixedPoints!![0].ProductCode!!*/
                        redeemGiftResponses.objCatalogueFixedPoints!!
                    )/* else holder.mSpinnerHost.adapter = ArrayAdapter(
                    holder.itemView.context,
                    R.layout.spinner_row_small_size,
                    defaultData
                )*/
            } else holder.mSpinnerHost.adapter = ArrayAdapter(
                holder.itemView.context,
                R.layout.spinner_row_small_size,
                defaultData
            )

            holder.mSpinnerHost.visibility = View.VISIBLE
        }

        if (redeemGiftResponses.productImage != null) {
            try {

                Glide.with(holder.itemView.context).asBitmap().error(R.drawable.ic_baseline_photo_size_select_actual_24)
                    .placeholder(R.drawable.ic_baseline_photo_size_select_actual_24).load(
                        redeemGiftResponses.productImage!!
                    )
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .optionalFitCenter().into(holder.voucher_image)

                holder.voucher_image.setPadding(0, 0, 0, 0)

            } catch (e: Exception) {}

        }

        holder.mSpinnerHost?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                objCatalogueFixedPoint = parent!!.getItemAtPosition(position) as ObjCatalogueFixedPoint

                Log.d("fjdskf", objCatalogueFixedPoint!!.fixedPoints.toString())


            }

        }


        holder.redeem.setOnClickListener {v ->
            if(BlockMultipleClick.click()) return@setOnClickListener

            if (holder.amount.text.toString().isNotEmpty()) amount = holder.amount.text.toString()

            if (redeemGiftResponses.product_type == 0)
                amount = objCatalogueFixedPoint!!.fixedPoints.toString()


            onItemClickListener.onRedeemVoucherFromAdapter(v,position,redeemGiftResponse.objCatalogueList!![position], amount)

        }

        holder.itemView.setOnClickListener {v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onDetailVoucherFromAdapter(v,position,redeemGiftResponse.objCatalogueList!![position])
        }



    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getItemCount(): Int {
        return redeemGiftResponse.objCatalogueList?.size!!
    }
}