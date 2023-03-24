package com.loyaltyworks.keshavcement.model.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.LstCashTransfer


class CashbackAdapter(context: Context, @LayoutRes resource: Int, locationStateCities: List<LstCashTransfer>) : ArrayAdapter<LstCashTransfer?>(context, resource, locationStateCities as List<LstCashTransfer?>) {
    private val locationStateCities: List<LstCashTransfer> = locationStateCities
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): LstCashTransfer? {
        return locationStateCities[position]
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
        category.setText(getItem(position)?.amount)

        if(position == 0) {
            category.setTextColor(context.resources.getColor(R.color.grey))
        }else{
            category.setTextColor(context.resources.getColor(R.color.black))
        }

//        if (position == 0) {
//            category.setTextColor(context.resources.getColor(R.color.referText))
//        }
        //AppController.hideProgressDialog(context);
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
        category.setText(getItem(position)?.amount)
        //AppController.hideProgressDialog(context);

        //Log.d("======DATA=======",this.getItem(position).getName());
        return convertView
    }

}