package com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer

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
import android.widget.*
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R

object CashTransferDialog {
    private var dialog: Dialog? = null

    interface CashTransferDialogCallBack {
        fun onOk()
        fun onClose()
    }

    fun showCashTransferDialog(
        context: Context,
        customerType: String,
        cashTransferDialogCallBack: CashTransferDialogCallBack,
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
        dialog?.setContentView(R.layout.cash_transfer_dialog)
        dialog?.show()

        val userLayout = dialog?.findViewById<View>(R.id.user_layout) as LinearLayout
        val userTypeSpinner = dialog?.findViewById<View>(R.id.user_type_spinner) as Spinner
        val userNameEdt = dialog?.findViewById<View>(R.id.name_edt) as EditText
        val nextBtn = dialog?.findViewById<View>(R.id.next_btn) as LinearLayout

        val otpLayout = dialog?.findViewById<View>(R.id.otp_layout) as LinearLayout
        val otpTxt = dialog?.findViewById<View>(R.id.otp_view_cash) as OtpTextView
        val otpNumber = dialog?.findViewById<View>(R.id.otp_number) as TextView
        val resendOtp = dialog?.findViewById<View>(R.id.resend_otp) as TextView
        val redeemBtn = dialog?.findViewById<View>(R.id.redeem_btn) as LinearLayout

        val successLayout = dialog?.findViewById<View>(R.id.success_layout) as LinearLayout
        val ok = dialog?.findViewById<View>(R.id.ok_btn) as LinearLayout

        val closeBtn = dialog?.findViewById<View>(R.id.close_btn) as ImageView

        if (customerType != BuildConfig.Dealer){
            userLayout.visibility = View.VISIBLE
        }else{
            userLayout.visibility = View.GONE
            otpLayout.visibility = View.VISIBLE
        }

        nextBtn.setOnClickListener {
            userLayout.visibility = View.GONE
            otpLayout.visibility = View.VISIBLE
        }

        redeemBtn.setOnClickListener {
            userLayout.visibility = View.GONE
            otpLayout.visibility = View.GONE
            closeBtn.visibility = View.GONE
            successLayout.visibility = View.VISIBLE
        }


        ok.setOnClickListener {
            cashTransferDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }

        closeBtn.setOnClickListener {
            cashTransferDialogCallBack.onClose()
            dialog?.dismiss()
            dialog = null
        }



    }
}