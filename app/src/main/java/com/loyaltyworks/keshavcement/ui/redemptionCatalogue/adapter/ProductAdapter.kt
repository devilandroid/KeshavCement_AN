package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowProductBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowProductBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowProductBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
        return 20
    }

}
