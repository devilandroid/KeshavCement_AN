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

object RedemptionTypeDialog {
    private var dialog: Dialog? = null

    interface RedemptionTypeDialogCallBack {
        fun forMyRedemptionClick()
        fun forCashTransferClick()
    }

    fun showRedemptionTypeDialog(
        context: Context,
        redemptionTypeDialogCallBack: RedemptionTypeDialogCallBack,
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
        dialog?.setContentView(R.layout.redemption_type_dialog)
        dialog?.show()


        val myRedemption = dialog?.findViewById<View>(R.id.my_redemption) as LinearLayout
        val cashTransfer = dialog?.findViewById<View>(R.id.cash_transfer) as LinearLayout

        myRedemption.setOnClickListener {
            redemptionTypeDialogCallBack.forMyRedemptionClick()
            dialog?.dismiss()
            dialog = null
        }

        cashTransfer.setOnClickListener {
            redemptionTypeDialogCallBack.forCashTransferClick()
            dialog?.dismiss()
            dialog = null
        }



    }
}