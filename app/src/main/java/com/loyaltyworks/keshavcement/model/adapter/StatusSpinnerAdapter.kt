package com.loyaltyworks.keshavcement.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.LstAttributesDetailStatus

class StatusSpinnerAdapter(context: Context?, statusList: List<LstAttributesDetailStatus>?):ArrayAdapter<LstAttributesDetailStatus?>(context!!, 0, statusList!!) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        // It is used to set our custom view.
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.spinner_row, parent, false)
        }
        val textViewName = convertView?.findViewById<TextView>(R.id.item_spinner)
        val currentItem = getItem(position)

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName?.text = currentItem.attributeValue
        }
        return convertView!!
    }
}