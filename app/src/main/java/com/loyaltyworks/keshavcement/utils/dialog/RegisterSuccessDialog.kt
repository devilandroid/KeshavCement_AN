package com.loyaltyworks.keshavcement.utils.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.loyaltyworks.keshavcement.R

object RegisterSuccessDialog {
    private var dialog: Dialog? = null

    interface RegisterSuccessDialogCallBack {
        fun onOk()
    }

    fun showRegisterSuccessDialog(
        context: Context,
        SuccessIcon:Boolean,
        title1:String,
        title2:String,
        registerSuccessDialogCallBack: RegisterSuccessDialogCallBack,
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
        dialog?.setContentView(R.layout.register_success_dialog)
        dialog?.show()

        val hint_text1 = dialog?.findViewById<View>(R.id.result_hints1) as TextView
        val hint_text2 = dialog?.findViewById<View>(R.id.result_hints2) as TextView
        val image = dialog?.findViewById<View>(R.id.image) as ImageView
        val ok = dialog?.findViewById<View>(R.id.back_to_dashboard) as LinearLayout

        hint_text1.text = title1
        hint_text2.text = title2

        if (SuccessIcon){
            image.visibility = View.VISIBLE
        }else{
            image.visibility = View.GONE
        }


        ok.setOnClickListener {
            registerSuccessDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}