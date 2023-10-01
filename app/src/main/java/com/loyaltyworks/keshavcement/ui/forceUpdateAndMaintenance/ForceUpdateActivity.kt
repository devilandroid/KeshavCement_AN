package com.loyaltyworks.keshavcement.ui.forceUpdateAndMaintenance

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import kotlinx.android.synthetic.main.activity_force_update.*

class ForceUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_force_update)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        forceupdate.setOnClickListener { v: View? ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.PLAY_STORE_LINK))
            startActivity(browserIntent)
            finish()
        }
    }
}