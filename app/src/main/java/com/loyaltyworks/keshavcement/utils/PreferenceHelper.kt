package com.loyaltyworks.keshavcement.utils


import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object PreferenceHelper {

    private const val PreferenceName = "LoyaltyPreferenceHelper_GP2020"

    private const val DashboardDetails = "LoyaltyPreferenceHelper_GP2020_uusAppOpenTodaysuu"

    private const val LoginDetails = "LoyaltyPreferenceHelper_GP2020_loginDetails"

    private const val SEDashboardDetails = "LoyaltyPreferenceHelper_uusDashboardDetail3suu"

    fun setBooleanValue(context: Context, key: String, value: Boolean){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanValue(context: Context, key: String): Boolean{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(key, false)
    }

    fun setStringValue(context: Context, key: String, value: String){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringValue(context: Context, key: String): String{
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, "")!!
    }



}