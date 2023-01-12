package com.loyaltyworks.keshavcement.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.loyaltyworks.keshavcement.R

object HelpTopicsDialog {

    private var dialog: Dialog? = null


    interface OnItemClickCallback {
        fun onHelpTopicsItemClicked()
    }


    fun showDialog(context:Context,onItemClickCallback: OnItemClickCallback){

        dialog = Dialog(context,R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog!!.setContentView(R.layout.layout_helptopics)
        dialog!!.show()


        val enrollmentRelated = dialog!!.findViewById<View>(R.id.enrollmentRelated) as RelativeLayout
        enrollmentRelated.setOnClickListener{

            dialog!!.dismiss()
            onItemClickCallback.onHelpTopicsItemClicked()
        }

    }


}