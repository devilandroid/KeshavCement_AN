package com.loyaltyworks.keshavcement.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.LstAttributesDetailLevel

class LevelListSpinnerAdapter(context: Context, resource: Int, cityList: List<LstAttributesDetailLevel>):
    ArrayAdapter<LstAttributesDetailLevel?>(context, resource, cityList as List<LstAttributesDetailLevel?>) {

        private val cityList: List<LstAttributesDetailLevel> = cityList

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): LstAttributesDetailLevel? {
        return cityList[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inf = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inf.inflate(R.layout.spinner_row, null)
        }
        val category = convertView!!.findViewById<View>(R.id.item_spinner) as TextView
        val layoutParams = category.layoutParams as LinearLayout.LayoutParams
        layoutParams.setMargins(8, 0, 0, 0)
        category.layoutParams = layoutParams
        category.setText(getItem(position)?.attributeValue)

        if(position == 0) {
            category.setTextColor(context.resources.getColor(R.color.black))
        }else{
            category.setTextColor(context.resources.getColor(R.color.black))
        }

        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            val inf = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inf.inflate(R.layout.spinner_popup_row, null)
        }
        val category = convertView!!.findViewById<View>(R.id.item_spinner) as TextView
        category.setText(getItem(position)?.attributeValue)
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView
    }
}