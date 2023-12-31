package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowCategoryBinding
import com.loyaltyworks.keshavcement.model.ObjCatalogueCategoryJson

class CategoryAdapter(val objCatalogueCategoryList: List<ObjCatalogueCategoryJson>, var selectedCategory: Int,
                      var onItemClickListener: OnItemClickCallBack) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    var tintPosition = -1
    var isClicked:Boolean = false

    interface OnItemClickCallBack {
        fun onCatagoryClickResponse(position: Int,objCategory: ObjCatalogueCategoryJson)
    }


    class ViewHolder(val binding: RowCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        var categoryName = binding.categoryName
        var categoryLayout = binding.categoryLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val objCategory = objCatalogueCategoryList[position]

        holder.categoryName.text = objCategory.catogoryName

        if (tintPosition == position) {
            holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.colorPrimary)
            holder.categoryName.setTextColor(holder.itemView.context.resources.getColor(R.color.dark))
        }else {
            holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.greyss)
            holder.categoryName.setTextColor(holder.itemView.context.resources.getColor(R.color.colorAccent))
        }

        if (!isClicked){
            if (objCategory.catogoryId == selectedCategory){
                holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.colorPrimary)
                holder.categoryName.setTextColor(holder.itemView.context.resources.getColor(R.color.dark))
            }

        }

        holder.itemView.setOnClickListener {
            tintPosition = position
            isClicked = true
            onItemClickListener.onCatagoryClickResponse(position,objCategory)
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return objCatalogueCategoryList.size
    }
}