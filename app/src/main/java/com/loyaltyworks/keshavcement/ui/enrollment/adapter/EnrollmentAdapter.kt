package com.loyaltyworks.keshavcement.ui.enrollment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loyaltyworks.keshavcement.databinding.RowEnrollmentBinding

class EnrollmentAdapter: RecyclerView.Adapter<EnrollmentAdapter.ViewHolder>() {

    class ViewHolder(val binding: RowEnrollmentBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowEnrollmentBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 10
    }
}