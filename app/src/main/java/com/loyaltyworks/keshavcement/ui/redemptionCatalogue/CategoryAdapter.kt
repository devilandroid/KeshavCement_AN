package com.loyaltyworks.keshavcement.ui.redemptionCatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowCategoryBinding

class CategoryAdapter/*(val objCatalogueCategoryList: List<ObjCatalogueCategoryJson>,var selectedCategory:String , var onItemClickListener: OnItemClickCallBack)*/ : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    /*var tintPosition = -1

    var isClicked:Boolean = false



    interface OnItemClickCallBack {
        fun onCatagoryClickResponse(position: Int,objCategory: ObjCatalogueCategoryJson)
    }*/


    class ViewHolder(val binding: RowCategoryBinding): RecyclerView.ViewHolder(binding.root) {

        var categoryName = binding.categoryName
        var categoryLayout = binding.categoryLayout

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       /* val objCategory = objCatalogueCategoryList[position]

        holder.categoryName.text = objCategory.catogoryName

        if (tintPosition == position) {
            holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.colorPrimary)
        }else {
            holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.DarkGrey)
        }

        if (!isClicked){

            if (objCategory.catogoryId == selectedCategory.toInt()){

                holder.categoryLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(R.color.colorPrimary)

            }

        }


        holder.itemView.setOnClickListener {
            onItemClickListener.onCatagoryClickResponse(position,objCategory)
            tintPosition = position
            isClicked = true
            notifyDataSetChanged()
        }*/

    }

    override fun getItemCount(): Int {
        //return objCatalogueCategoryList.size
        return 10
    }
}