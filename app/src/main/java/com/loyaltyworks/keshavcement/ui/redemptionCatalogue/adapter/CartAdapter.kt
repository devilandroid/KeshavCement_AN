package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCartBinding
import com.loyaltyworks.keshavcement.model.CatalogueSaveCartDetailResponse
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper
import com.loyaltyworks.keshavcement.utils.dialog.LoadingDialogue

class CartAdapter(val cartList : List<CatalogueSaveCartDetailResponse>, var deleteProductCallback: DeleteProductCallback):RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    private var isClick :Boolean=false
    var stopTextChanger = false

    interface DeleteProductCallback {
        fun onQuntityIncDescCallback(catalogueCartList: CatalogueSaveCartDetailResponse, Qty: Int, isClick: Boolean)
        fun onRemoveCartItemCallback(position: Int, cartList: CatalogueSaveCartDetailResponse)
    }

    class ViewHolder(binding: RowCartBinding) : RecyclerView.ViewHolder(binding.root) {
        val prodImg = binding.productImg
        val prodName = binding.productName
        val points = binding.points
        val cartprodcount = binding.cartProdCount

        val minusBtn = binding.minusBtn
        val plusBtn = binding.plusBtn
        val removeCartBtn = binding.removeCartItem
        val category = binding.categoryName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = cartList[position]

        holder.prodName.text = data.productName
        holder.points.text = data.pointsRequired.toString()
        holder.cartprodcount.text = data.noOfQuantity.toString()
        holder.category.text = data.catogoryName.toString()

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + data.productImage)
            .into(holder.prodImg)

        if((itemCount -1)==cartList.size-1 || position == 0){
            isClick = false
        }

        /*** Cart Item Remove call back ***/
        holder.removeCartBtn.setOnClickListener {
            if(BlockMultipleClick.click()) return@setOnClickListener

            deleteProductCallback.onRemoveCartItemCallback(position,data)
        }


        /*** Cart Item Decrease call back ***/
        holder.minusBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
//            onItemCountClickListener.onCartItemCountClickResponse(v,position)

            isClick = true
            if (holder.cartprodcount.text.toString().isEmpty() || holder.cartprodcount.text.toString().toInt() == 1)
                return@setOnClickListener

            LoadingDialogue.showDialog(holder.itemView.context)

            stopTextChanger = true
            deleteProductCallback.onQuntityIncDescCallback(
                data,
                holder.cartprodcount.text.toString().toInt() - 1, isClick
            )
            var currCountMin: Int = holder.cartprodcount.text.toString().toInt()
            --currCountMin
            holder.cartprodcount.setText(currCountMin.toString())
            data.noOfQuantity = holder.cartprodcount.text.toString().toInt()
        }

        var totalPointsToRedeem:Int = data.sumofTotalPointsRequired!!.floatToInt() + data.pointsPerUnit!!.toInt()
        /*** Cart Item Increase call back ***/
        holder.plusBtn.setOnClickListener { v ->
            if(BlockMultipleClick.click()) return@setOnClickListener
//            onItemCountClickListener.onCartItemCountClickResponse(v,position)
            if (PreferenceHelper.getStringValue(holder.itemView.context,BuildConfig.RedeemablePointsBalance).toInt() >= totalPointsToRedeem){
                isClick = true
                LoadingDialogue.showDialog(holder.itemView.context)
                stopTextChanger = true
                if (holder.cartprodcount.text.toString().isEmpty()) {
                    deleteProductCallback.onQuntityIncDescCallback(data, 1, isClick)
                } else {
                    deleteProductCallback.onQuntityIncDescCallback(data, holder.cartprodcount.text.toString().toInt() + 1, isClick
                    )
                }

                var currentCount: Int
                currentCount =
                    if (holder.cartprodcount.text.toString().isEmpty()) 0 else holder.cartprodcount.text.toString()
                        .toInt()
                ++currentCount
                holder.cartprodcount.setText(currentCount.toString())
                data.noOfQuantity = holder.cartprodcount.text.toString().toInt()
            }else{
                LoadingDialogue.dismissDialog()
                Toast.makeText(holder.itemView.context,holder.itemView.context.getString(R.string.insufficient_point_balance_to_redeem), Toast.LENGTH_SHORT).show()
            }

        }

    }

    fun String.floatToInt(): Int {
        return this.toFloat().toInt()
    }

    override fun getItemCount(): Int {
        return cartList.size
    }
}