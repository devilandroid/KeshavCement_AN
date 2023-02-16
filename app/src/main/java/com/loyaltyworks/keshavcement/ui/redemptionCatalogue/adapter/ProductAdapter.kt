package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowProductBinding
import com.loyaltyworks.keshavcement.model.ObjCataloguee
import com.loyaltyworks.keshavcement.utils.BlockMultipleClick
import com.loyaltyworks.keshavcement.utils.PreferenceHelper

class ProductAdapter(val objCatalogueList: List<ObjCataloguee>, var onItemClickListener: OnItemClickCallBack) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    interface OnItemClickCallBack {
        fun onProductListItemClickResponse(itemView: View, position: Int, objCatalogue: ObjCataloguee, )
        fun onAddToCart(objCatalogue: ObjCataloguee)
        fun onAddPlannerListener(catalogueId: Int)
        fun onHoldAddtoCart()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class ViewHolder(binding: RowProductBinding, pos: Int) :
        RecyclerView.ViewHolder(binding.root) {
        var prodImage = binding.productImg
        var prodName = binding.productName
        var pointValue = binding.points
        var addToCartButton = binding.addToCartButton
        var addToCartLayout = binding.addCartLayout
        var category = binding.categoryName

        init {

            val objCatalogue = objCatalogueList[pos]

            /*if (objCatalogue.pointsRequired!!.toInt() <= PreferenceHelper.getStringValue(itemView.context,BuildConfig.RedeemablePointsBalance).toInt()) {

                if (objCatalogue.catalogueIdExist == "1") {
                    addToCartButton.text = itemView.context.getString(R.string.added_to_cart)
                    addToCartLayout.background = itemView.context.getDrawable(R.drawable.product_corner_bg_grey)
                } else if (objCatalogue.catalogueIdExist == "0") {
                    addToCartButton.text = itemView.context.getString(R.string.add_to_cart)
                    addToCartLayout.background = itemView.context.getDrawable(R.drawable.product_corner_bg_dark)
                }

            } else {

                if (objCatalogue.isPlanner == true) {
                    addToCartLayout.visibility = View.VISIBLE

                    addToCartButton.text = itemView.context.getString(R.string.add_to_planner)

                    if (objCatalogue.isAddPlanner == true) {
                        addToCartLayout.background = itemView.context.getDrawable(R.drawable.product_corner_bg_grey)
                        addToCartButton.text = itemView.context.getString(R.string.added_to_planner)
                    }
                } else
                    addToCartLayout.visibility = View.GONE
            }*/


        }

    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding, viewType)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objCatalogue = objCatalogueList[position]

        if (objCatalogue.pointsRequired!!.toInt() <= PreferenceHelper.getStringValue(holder.itemView.context,BuildConfig.RedeemablePointsBalance).toInt()) {

            if (objCatalogue.catalogueIdExist == "1") {
                holder.addToCartButton.text = holder.itemView.context.getString(R.string.added_to_cart)
                holder.addToCartLayout.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_grey)
            } else if (objCatalogue.catalogueIdExist == "0") {
                holder.addToCartButton.text = holder.itemView.context.getString(R.string.add_to_cart)
                holder.addToCartLayout.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_dark)
            }

        } else {

            if (objCatalogue.isPlanner == true) {
                holder.addToCartLayout.visibility = View.VISIBLE

                holder.addToCartButton.text = holder.itemView.context.getString(R.string.add_to_planner)

                if (objCatalogue.isAddPlanner == true) {
                    holder.addToCartLayout.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_grey)
                    holder.addToCartButton.text = holder.itemView.context.getString(R.string.added_to_planner)
                }
            } else
                holder.addToCartLayout.visibility = View.GONE
        }

        holder.prodName.text = objCatalogue.productName
        holder.pointValue.text = objCatalogue.pointsRequired.toString()
        holder.category.text = "/ " + objCatalogue.catogoryName

        Glide.with(holder.itemView.context).asBitmap()
            .error(R.drawable.ic_default_img)
            .load(BuildConfig.CATALOGUE_IMAGE_BASE + objCatalogue.productImage)
            .into(holder.prodImage)

        holder.addToCartLayout.setOnClickListener {

            if (objCatalogue.pointsRequired!!.toInt() <= PreferenceHelper.getStringValue(holder.itemView.context,BuildConfig.RedeemablePointsBalance).toInt()) {
                var abc: String =
                    PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus.toString()
                Log.d("dhjbhf", " a " + abc)
                if (objCatalogue.catalogueIdExist == "0") {
                    if (PreferenceHelper.getDashboardDetails(holder.itemView.context)?.lstCustomerFeedBackJsonApi!![0].verifiedStatus == 1) {

                        onItemClickListener.onAddToCart(objCatalogue)
                        objCatalogue.catalogueIdExist = "1"
                        holder.addToCartButton.text = holder.itemView.context.getString(R.string.added_to_cart)
                        holder.addToCartLayout.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_grey)

                    } else {

                        onItemClickListener.onHoldAddtoCart()


                    }

                } else {
                    Toast.makeText(holder.itemView.context, holder.itemView.context.getString(R.string.already_added_in_cart), Toast.LENGTH_SHORT).show()
                }
            } else {
                onItemClickListener.onAddPlannerListener(objCatalogue.catalogueId!!)
                objCatalogue.isAddPlanner = true
                holder.addToCartButton.text = holder.itemView.context.getString(R.string.added_to_planner)
                holder.addToCartLayout.background = holder.itemView.context.getDrawable(R.drawable.product_corner_bg_grey)

            }
        }

        holder.itemView.setOnClickListener { v ->
            if (BlockMultipleClick.click()) return@setOnClickListener
            onItemClickListener.onProductListItemClickResponse(v, position, objCatalogue)

        }
    }

    override fun getItemCount(): Int {
        return objCatalogueList.size
    }

}
