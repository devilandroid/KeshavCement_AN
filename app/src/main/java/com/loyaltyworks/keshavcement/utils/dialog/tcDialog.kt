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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.LstTermsAndCondition
import com.loyaltyworks.keshavcement.utils.language_locale.LocaleManager


object tcDialog {
    private var dialog: Dialog? = null

    interface TCDialogueCallBack {
        fun onOk()
        fun onCancel()
    }


    fun showTCDialog(
        context: Context,
        from: String,
        lstTermsAndCondition: List<LstTermsAndCondition>,
        tcDialogueCallBack: TCDialogueCallBack
    ) {

        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog3)
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
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        dialog?.setContentView(R.layout.fragment_terms_and_condition)
        dialog?.show()


        val ok = dialog?.findViewById<View>(R.id.accept_btn) as TextView
        val cancel = dialog?.findViewById<View>(R.id.declien_btn) as TextView
        val mWebview = dialog?.findViewById<View>(R.id.webview) as WebView
        val btn = dialog?.findViewById<View>(R.id.btn_layout) as ConstraintLayout

        if (from.contentEquals("Login")) {
            btn.visibility = View.VISIBLE
        } else {
            btn.visibility = View.GONE
        }


        val webSetting: WebSettings = mWebview.settings
        webSetting.builtInZoomControls = false
        webSetting.javaScriptEnabled = true
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        val res = context.resources
        val fontSize = res.getDimension(R.dimen.text).toInt()
        webSetting.defaultFontSize = fontSize

        mWebview.webViewClient = WebViewClient()

        if (LocaleManager.getLanguagePref(context).equals(LocaleManager.ENGLISH,true)) {
            for (i in lstTermsAndCondition){
                if (i.language.equals("English") && i.html != null && !i.html.isNullOrEmpty()){
//                            binding.webview.loadData(i.html!!,"text/html", "UTF-8")
                    mWebview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                }
            }

        }else if (LocaleManager.getLanguagePref(context).equals(LocaleManager.HINDI,true)){
            for (i in lstTermsAndCondition){
                if (i.language.equals("Hindi") && i.html != null && !i.html.isNullOrEmpty()){
                    mWebview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                }
            }
        }else if (LocaleManager.getLanguagePref(context).equals(LocaleManager.KANNADA,true)){
            for (i in lstTermsAndCondition){
                if (i.language.equals("Kannada") && i.html != null && !i.html.isNullOrEmpty()){
                    mWebview.loadDataWithBaseURL(null, i.html!!,null,"text/html", "UTF-8")
                }
            }
        }


        ok.setOnClickListener {
            tcDialogueCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }

        cancel.setOnClickListener {
            tcDialogueCallBack.onCancel()
            dialog?.dismiss()
            dialog = null
        }


    }


}