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
import com.loyaltyworks.keshavcement.R

object DeliveryTypeDialog {
    private var dialog: Dialog? = null

    interface DeliveryTypeDialogCallBack {
        fun forSelfClick()
        fun forOthersClick()
    }

    fun showDeliveryTypeDialog(
        context: Context,
        deliveryTypeDialogCallBack: DeliveryTypeDialogCallBack,
    ) {

//        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setGravity(Gravity.CENTER)
        dialog?.setCancelable(true)
        dialog?.setCanceledOnTouchOutside(true)
        val window = dialog!!.window
        window!!.setGravity(Gravity.CENTER)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialog?.setContentView(R.layout.delivery_type_dialog)
        dialog?.show()


        val forSelf = dialog?.findViewById<View>(R.id.for_self) as LinearLayout
        val forOthers = dialog?.findViewById<View>(R.id.for_others) as LinearLayout

        forSelf.setOnClickListener {
            deliveryTypeDialogCallBack.forSelfClick()
            dialog?.dismiss()
            dialog = null
        }

        forOthers.setOnClickListener {
            deliveryTypeDialogCallBack.forOthersClick()
            dialog?.dismiss()
            dialog = null
        }



    }
}