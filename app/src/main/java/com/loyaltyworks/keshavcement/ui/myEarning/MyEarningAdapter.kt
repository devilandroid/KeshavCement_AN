package com.loyaltyworks.keshavcement.ui.myEarning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.databinding.RowMyEarningsBinding
import com.loyaltyworks.keshavcement.databinding.RowMyRedemptionsBinding

class MyEarningAdapter : RecyclerView.Adapter<MyEarningAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowMyEarningsBinding): RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        val binding = RowMyEarningsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



    }

    override fun getItemCount(): Int {
        return 10
    }

}
