package com.loyaltyworks.keshavcement.utils.dialog

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
import com.loyaltyworks.keshavcement.R

object InstructionDialog {
    private var dialog: Dialog? = null

    interface InstructionDialogCallBack {
        fun onOk()
    }

    fun showInstructionDialog(
        context: Context,
        instructionDialogCallBack: InstructionDialogCallBack,
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
        dialog?.setContentView(R.layout.instruction_dialog)
        dialog?.show()

        val ok = dialog?.findViewById<View>(R.id.ok_btn) as LinearLayout
        val _mWebview1 = dialog?.findViewById<View>(R.id.instruction_webView) as WebView


        val webSetting: WebSettings = _mWebview1.settings
        webSetting.builtInZoomControls = false
        webSetting.javaScriptEnabled = true
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
//        val res = context.resources
//        val fontSize = res.getDimension(R.dimen.h6).toInt()
//        webSetting.defaultFontSize = fontSize as Int

        _mWebview1.webViewClient = WebViewClient()
        _mWebview1.loadUrl("file:///android_asset/cash_transfer_instruction.html")


        ok.setOnClickListener {
            instructionDialogCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }



    }
}