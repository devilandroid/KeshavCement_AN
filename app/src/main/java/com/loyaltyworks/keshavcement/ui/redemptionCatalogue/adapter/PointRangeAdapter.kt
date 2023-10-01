package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowPointRangeBinding
import com.loyaltyworks.keshavcement.model.PointRange
import java.util.ArrayList

class PointRangeAdapter(val pointRange: List<PointRange>, var selectedPointRange: Int, var onItemClickListener: OnItemClickCallBack): RecyclerView.Adapter<PointRangeAdapter.ViewHolder>() {
    var tintPosition = -1
    var isClicked:Boolean = false

    interface OnItemClickCallBack {
        fun onPointRangeClickResponse(position: Int,pointRange: PointRange)
    }

    class ViewHolder(val binding: RowPointRangeBinding): RecyclerView.ViewHolder(binding.root) {
        var pointRangeName = binding.pointRangeName
        var pointRangeLayout = binding.pointRangeLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowPointRangeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = pointRange[position]

        holder.pointRangeName.text = data.name

        if (tintPosition == position) {
            holder.pointRangeLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(
                R.color.colorPrimary)
            holder.pointRangeName.setTextColor(holder.itemView.context.resources.getColor(R.color.dark))
        }else {
            holder.pointRangeLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(
                R.color.greyss)
            holder.pointRangeName.setTextColor(holder.itemView.context.resources.getColor(R.color.colorAccent))
        }

        if (!isClicked){
            if (data.id == selectedPointRange){
                holder.pointRangeLayout.backgroundTintList = holder.itemView.context.resources.getColorStateList(
                    R.color.colorPrimary)

                holder.pointRangeName.setTextColor(holder.itemView.context.resources.getColor(R.color.dark))
            }
        }

        holder.itemView.setOnClickListener {
            tintPosition = position
            isClicked = true
            onItemClickListener.onPointRangeClickResponse(position,data)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return pointRange.size
    }
}