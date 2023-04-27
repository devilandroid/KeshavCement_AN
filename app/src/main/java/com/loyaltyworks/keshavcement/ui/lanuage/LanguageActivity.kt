package com.loyaltyworks.keshavcement.ui.lanuage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.loyaltyworks.keshavcement.R
import com.loyaltyworks.keshavcement.baseClass.BaseActivity
import com.loyaltyworks.keshavcement.ui.login.LoginActivity

class LanguageActivity : BaseActivity(), LanguageFragment.LanguageListener {
    lateinit var navController: NavController
    override fun callInitialServices() {

    }

    override fun callObservers() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        navController = findNavController(R.id.nav_host_language_fragment)
    }

    override fun onLanguageSelect(language: String) {
        Log.d("TAG_Language", "onLanguageSelect: $language")
        if (language == "en"){
            (this as BaseActivity).setNewLocaleWithoutRefresh(this as AppCompatActivity,language)

            startActivity(Intent(this, LoginActivity::class.java))
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
        }else{
            Toast.makeText(this, "This language is coming soon!", Toast.LENGTH_SHORT).show()

        }


    }
}