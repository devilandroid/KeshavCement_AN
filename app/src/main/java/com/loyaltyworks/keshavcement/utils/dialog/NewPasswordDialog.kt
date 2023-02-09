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
import android.widget.*
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.ui.redemptionCatalogue.cashTransfer.CashTransferDialog

object NewPasswordDialog {
    private var dialog: Dialog? = null

    interface NewPasswordDialogCallBack {
        fun onSubmit(newPassword:String,confirmPassword:String)
    }

    fun showNewPasswordDialog(
        context: Context,
        newPasswordDialogCallBack: NewPasswordDialogCallBack,
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
        dialog?.setContentView(R.layout.new_password_dialog)
        dialog?.show()

        val newPassw = dialog?.findViewById<View>(R.id.new_passw) as EditText
        val confirmPassw = dialog?.findViewById<View>(R.id.confirm_passw) as EditText
        val submitPassword = dialog?.findViewById<View>(R.id.submit_password) as LinearLayout

        submitPassword.setOnClickListener {
            if (newPassw.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "Enter new password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (confirmPassw.text.toString().isNullOrEmpty()){
                Toast.makeText(context, "Enter confirm password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else if (newPassw.text.toString() != confirmPassw.text.toString()){
                Toast.makeText(context, "Password mismatch!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else {
                newPasswordDialogCallBack.onSubmit(newPassw.text.toString(), confirmPassw.text.toString())
//                dialog?.dismiss()
//                dialog = null
            }
            
        }



    }

    fun dismissChangePasswDialog(){
        dialog?.dismiss()
        dialog = null
    }
}