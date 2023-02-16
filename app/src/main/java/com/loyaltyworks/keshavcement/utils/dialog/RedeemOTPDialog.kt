package com.loyaltyworks.keshavcement.utils.dialog

import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
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
    lateinit var timers: CountDownTimer
    var START_MILLI_SECONDS = 60000L

    interface RedeemOTPDialogCallBack {
        fun onOk()
        fun onRedeemClick(otp: String)
        fun resendOTP()
    }

    fun showRedeemOTPDialog(
        context: Context,
        otpMobile: String,
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
        val otpTimer = dialog?.findViewById<View>(R.id.timer_dialog) as TextView
        val closeBtn = dialog?.findViewById<View>(R.id.close_btn) as ImageView

        otpNumber.text = "OTP will receive at " + otpMobile

        /*** Timer Start ***/
        if (otpTimer.text.isNotEmpty()) {
            otpTimer.text = ""
        }

        timers = object : CountDownTimer(START_MILLI_SECONDS, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                try {
                    otpTimer.visibility = View.VISIBLE
                    resendOtp.visibility = View.GONE
                    otpTimer.text = "00 : " + (millisUntilFinished / 1000).toString()
                } catch (e: Exception) {
                }
            }
            override fun onFinish() {
                try {
                    otpTimer.text = "00 : 00"
                    otpTimer.visibility = View.GONE
                    resendOtp.visibility = View.VISIBLE
                } catch (e: Exception) {
                }

            }

        }
        timers.start()
        /*** Timer Close ***/

        /*** Resend OTP Start ***/
        resendOtp.setOnClickListener{
            redeemOTPDialogCallBack.resendOTP()

            if (otpTimer.text.isNotEmpty()) {
                otpTimer.text = ""
            }

            timers = object : CountDownTimer(START_MILLI_SECONDS, 1000L) {
                override fun onTick(millisUntilFinished: Long) {
                    try {
                        otpTimer.visibility = View.VISIBLE
                        resendOtp.visibility = View.GONE
                        otpTimer.text = "00 : " + (millisUntilFinished / 1000).toString()
                    } catch (e: Exception) {
                    }
                }
                override fun onFinish() {
                    try {
                        otpTimer.text = "00 : 00"
                        otpTimer.visibility = View.GONE
                        resendOtp.visibility = View.VISIBLE
                    } catch (e: Exception) {
                    }

                }

            }
            timers.start()

        }
        /*** Resend OTP Close ***/

        redeemBtn.setOnClickListener {
            if (otpTxt.otp.isNullOrEmpty()){
                Toast.makeText(context, "Enter OTP!", Toast.LENGTH_SHORT).show()
            }else {
                redeemOTPDialogCallBack.onRedeemClick(otpTxt.otp!!)
//                dialog?.dismiss()
//                dialog = null
            }
            
        }
        
        closeBtn.setOnClickListener {
            redeemOTPDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }

    fun hideDialog() {
        dialog?.dismiss()
        dialog = null
    }
}