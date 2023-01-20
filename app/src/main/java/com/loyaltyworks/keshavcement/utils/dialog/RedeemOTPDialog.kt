package com.loyaltyworks.keshavcement.utils.dialog

import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer.CashTransferDialog

object RedeemOTPDialog {
    private var dialog: Dialog? = null

    interface RedeemOTPDialogCallBack {
        fun onOk()
        fun onRedeemClick()
    }

    fun showRedeemOTPDialog(
        context: Context,
        otp: String,
        redeemOTPDialogCallBack: RedeemOTPDialogCallBack,
    ) {

        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog)
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
        dialog?.setContentView(R.layout.redeem_otp_dialog)
        dialog?.show()

        val otpTxt = dialog?.findViewById<View>(R.id.otp_view_cash) as OtpTextView
        val otpNumber = dialog?.findViewById<View>(R.id.otp_number) as TextView
        val resendOtp = dialog?.findViewById<View>(R.id.resend_otp) as TextView
        val redeemBtn = dialog?.findViewById<View>(R.id.redeem_btn) as LinearLayout
        
        val closeBtn = dialog?.findViewById<View>(R.id.close_btn) as ImageView


        redeemBtn.setOnClickListener {
            if (otpTxt.otp.isNullOrEmpty()){
                Toast.makeText(context, "Enter OTP!", Toast.LENGTH_SHORT).show()
            }else if (otpTxt.otp == otp){
                redeemOTPDialogCallBack.onRedeemClick()
                dialog?.dismiss()
                dialog = null
            }else{
                Toast.makeText(context, "Enter valid OTP!", Toast.LENGTH_SHORT).show()
            }
            
        }
        
        closeBtn.setOnClickListener {
            redeemOTPDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}