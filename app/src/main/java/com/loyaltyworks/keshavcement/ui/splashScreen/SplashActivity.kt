package com.loyaltyworks.keshavcement.ui.splashScreen

import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loyaltyworks.keshavcement.BuildConfig
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.baseClass.BaseActivity
import com.loyaltyworks.keshavcement.ui.DashboardActivity
import com.loyaltyworks.keshavcement.ui.lanuage.LanguageActivity
import com.loyaltyworks.keshavcement.ui.login.LoginActivity
import com.loyaltyworks.keshavcement.utils.PreferenceHelper

class SplashActivity : BaseActivity() {
    private lateinit var splashScreenViewModel: SplashScreenViewModel

    private var PLAY_STORE_LINK =
        "https://play.google.com/store/apps/details?id=com.loyaltyworks.keshavcement&hl=en"

    override fun callInitialServices() {

    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashScreenViewModel = ViewModelProvider(this).get(SplashScreenViewModel::class.java)

        val packageInfo: PackageInfo = packageManager!!.getPackageInfo(packageName, 0)
        Log.d("hfjshjrf", packageInfo.versionCode.toString())


        splashScreenViewModel.checkIfUpdateAvailabe(this, packageInfo.versionCode)


        splashScreenViewModel.get_isUpdateAvailable()!!.observe(this, Observer {

            if (it) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK))
                startActivity(browserIntent)
            } else {

                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    when {
                        PreferenceHelper.getBooleanValue(this, BuildConfig.IsLoggedIn) -> {
                            val intent = Intent(this, DashboardActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                        else -> {
                            startActivity(Intent(this, LanguageActivity::class.java))
                            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
                        }
                    }
                    finish()
                }, 2000)
            }
        })

    }
}