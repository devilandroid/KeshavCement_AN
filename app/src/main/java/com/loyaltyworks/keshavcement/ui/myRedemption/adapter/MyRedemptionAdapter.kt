package com.loyaltyworks.keshavcement.ui.myRedemption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowMyRedemptionsBinding

class MyRedemptionAdapter : RecyclerView.Adapter<MyRedemptionAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowMyRedemptionsBinding): RecyclerView.ViewHolder(binding.root) {

        val status = binding.status
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowMyRedemptionsBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return 10
    }

}
