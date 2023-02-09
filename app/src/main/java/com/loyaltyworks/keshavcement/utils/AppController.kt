package com.loyaltyworks.keshavcement.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.app.Activity
import android.graphics.Bitmap
import android.util.Base64
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.rongi.rotate_layout.layout.RotateLayout
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.model.ObjImageGalleryList
import java.io.ByteArrayOutputStream


object AppController {

    interface CustomAlertCallbacks {
        fun onPositiveAction(mDialogs: Dialog?)
        fun onNegetiveActon(mDialogs: Dialog?)
    }


    /* change MMM dd yyyy to dd/mm/yy */
    fun changeDateFormate(changeDate:String): String {
        var dString:String
        try {
            val sdf = SimpleDateFormat("MMM dd yyyy")
            val d = sdf.parse(changeDate)
            sdf.applyPattern("dd/MM/yyyy").toString();
            dString =   sdf.format(d).toString()

        } catch (ex: ParseException) {
            Log.v("Exception", ex.localizedMessage)
            dString = changeDate
        }
        return dString
    }


    fun dateFormat(reqDate: String): String? {
        val year = reqDate.split("-".toRegex()).toTypedArray()[0]
        val day = reqDate.split("-".toRegex()).toTypedArray()[2]
        val month = reqDate.split("-".toRegex()).toTypedArray()[1]
        return "$day/$month/$year"
    }

    fun dateUIFormat(reqDate: String): String? {
        val year = reqDate.split("/".toRegex()).toTypedArray()[2]
        val day = reqDate.split("/".toRegex()).toTypedArray()[1]
        val month = reqDate.split("/".toRegex()).toTypedArray()[0]
        return "$year/$day/$month"
    }



    fun dateAPIFormats(reqDate: String?): String? {
        if (reqDate != null && !reqDate.isEmpty()) {
            val dateFor: String = reqDate
            return dateFor.split("/".toRegex())
                    .toTypedArray()[2] + "-" + dateFor.split("/".toRegex())
                    .toTypedArray()[1] + "-" + dateFor.split("/".toRegex()).toTypedArray()[0]
        }
        return reqDate
    }

    fun dateAPIFormat(reqDate: String?): String? {
        return reqDate?.split("/".toRegex())!!.toTypedArray()[1] + "/" + reqDate.split("/".toRegex())
            .toTypedArray()[0] + "/" + reqDate.split("/".toRegex()).toTypedArray()[2]
    }




    fun dateToNewUIFormatss(reqDate: String): String? {  // input date : mm/dd/yyyy hh:MM:ss am/pm
        val dateFor = reqDate.split(" ".toRegex()).toTypedArray()[0]
        val time = reqDate.split(" ".toRegex()).toTypedArray()[1]
        val ampm = reqDate.split(" ".toRegex()).toTypedArray()[2]
        return dateFor.split("/".toRegex()).toTypedArray()[1] + "/" + dateFor.split("/".toRegex()).toTypedArray()[0] + "/" + dateFor.split(
            "/".toRegex()
        ).toTypedArray()[2] + " " + time + " " + ampm

//        return reqDate;
    }

    fun dateToNewUIFormats(reqDate: String): String? {  // input date : mm/dd/yyyy hh:MM:ss am/pm
        return reqDate.split("/".toRegex()).toTypedArray()[1] + "/" + reqDate.split("/".toRegex())
            .toTypedArray()[0] + "/" + reqDate.split("/".toRegex()).toTypedArray()[2]
    }


    fun encryptionBeforeRegd(str: String?, key: String?): String? {
        var str = str
        var key = key
        if (key == null || key.length <= 0) return str
        if (str == null) str = ""
        if (key.toString().length > 8) key = key.substring(0, 8)
        var prand = ""
        for (i in 0 until key.length) {
            prand += key[i].toInt()
        }
        val sPos = Math.floor((prand.length / 5).toDouble()).toInt()
        var preMult = (if (prand[sPos] == '0') '1' else prand[sPos]).toString() + ""
        preMult = preMult + prand[sPos * 2] + prand[sPos * 3] + prand[sPos * 4] + prand[sPos * 5]
        val mult = preMult.toLong()
        val incr = Math.ceil((key.length / 2).toDouble()).toInt()
        val modu = (Math.pow(2.0, 31.0) - 1).toInt()
        if (mult < 2) {
            return null
        }
        val salt = Math.round(Math.random() * 1000000000) % 100000000
        prand += salt
        while (prand.length > 10) {
            prand = prand.substring(0, 10)
        }
        prand = ((mult * prand.toLong() + incr) % modu).toString()
        var enc_chr = ""
        var enc_str: String? = ""
        for (i in 0 until str.length) {
            enc_chr = (str[i].toInt() xor Math.floor(prand.toDouble() / modu.toDouble() * 255)
                .toInt()).toString()
            enc_str += if (enc_chr.toInt() < 16) "0" + Integer.toHexString(enc_chr.toInt()) else Integer.toHexString(
                enc_chr.toInt()
            )
            prand = ((mult * prand.toInt() + incr) % modu).toString()
        }
        var random_salt = java.lang.Long.toHexString(salt)
        while (random_salt.length < 8) random_salt = "0$random_salt"
        enc_str += salt
        return enc_str
    }


  /*  Custom Dialog  */

    private var dialog: Dialog?= null

    interface SuccessCallBack {
        fun onOk()
    }

    fun showSuccessPopUpDialog(context: Context ,msgText :String, SuccessCallBack: SuccessCallBack) {

        if (dialog != null) return

        dialog = Dialog(context, R.style.Theme_Dialog)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

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
        dialog?.setContentView(R.layout.custom_alert)
        dialog?.show()

        val ok = dialog?.findViewById<View>(R.id.text_ok) as LinearLayout
        val textDialog = dialog?.findViewById<View>(R.id.textDialog) as TextView
        textDialog.text = msgText
        ok.setOnClickListener {
            SuccessCallBack.onOk()
            dialog?.dismiss()
            dialog = null
        }

    }


    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    fun BitmaptoBase64Convert(bitmap: Bitmap): String? {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, stream) //compress to which format you want.
        val byte_arr = stream.toByteArray()
        return Base64.encodeToString(byte_arr, Base64.NO_WRAP)
    }


    var PromotionPopUpDialogue: Dialog? = null
    public fun popUpPromotionDialogue(context: Context?, position: Int, objImageGalleryList: List<ObjImageGalleryList>) {
        PromotionPopUpDialogue = null
        if (PromotionPopUpDialogue == null) {
            PromotionPopUpDialogue = Dialog(context!!, R.style.Theme_Dialog2)
            PromotionPopUpDialogue!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            // clear background dim effect
//                 dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            PromotionPopUpDialogue!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            PromotionPopUpDialogue!!.setContentView(R.layout.promotion_popup)
            val window = PromotionPopUpDialogue!!.window
            window!!.setLayout(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            PromotionPopUpDialogue!!.setCanceledOnTouchOutside(true)
            PromotionPopUpDialogue!!.setCancelable(true)
        }

        val mClose = PromotionPopUpDialogue!!.findViewById<ImageView>(R.id.close_btn)
        val mPromotionImageView = PromotionPopUpDialogue!!.findViewById<ImageView>(R.id.selectedImage)

        Log.d("hfrg", "ijhfjh   " + BuildConfig.PROMO_IMAGE_BASE + objImageGalleryList[position].imageGalleryUrl?.replace("~", ""))

        Glide.with(context!!)
            .load(
                BuildConfig.PROMO_IMAGE_BASE + objImageGalleryList[position].imageGalleryUrl?.replace("~", "")
            )
            .error(R.drawable.ic_default_img)
            .fitCenter()
            .into(mPromotionImageView)

        /*** for rotate image after open dialog ***/
        val rotateLayout = PromotionPopUpDialogue!!.findViewById<RotateLayout>(R.id.rotation_layout)
        val newAngle: Int = rotateLayout.getAngle() + 270
        rotateLayout.setAngle(newAngle)

        mClose.setOnClickListener {
            PromotionPopUpDialogue!!.dismiss()
            PromotionPopUpDialogue = null
        }
        PromotionPopUpDialogue!!.show()

    }


}