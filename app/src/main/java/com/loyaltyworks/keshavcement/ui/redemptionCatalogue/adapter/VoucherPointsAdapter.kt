package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.ObjCatalogueFixedPoint

class VoucherPointsAdapter(context: Context?, private val algorithmList: List<ObjCatalogueFixedPoint>) : ArrayAdapter<ObjCatalogueFixedPoint?>(
    context!!, 0, algorithmList
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    fun getPosition(id: Int): Int {
        algorithmList.forEachIndexed { index, country ->
            if (id == country.fixedPoints)
                return index
        }
        return 0
    }

    private fun initView(
        position: Int, convertView: View?,
        parent: ViewGroup
    ): View {
        // It is used to set our custom view.
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.custom_spinner_layout, parent, false)
        }
        val textViewName = convertView?.findViewById<TextView>(R.id.text_view)
        val currentItem = getItem(position)

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            textViewName?.text = currentItem.fixedPoints.toString()
        }
        return convertView!!
    }
}