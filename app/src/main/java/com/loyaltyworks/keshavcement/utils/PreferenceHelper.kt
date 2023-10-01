package com.loyaltyworks.keshavcement.utils


import android.content.Context
import com.loyaltyworks.keshavcement.model.LoginResponse
import com.loyaltyworks.keshavcement.model.UpdatedDashboardSingleApiResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi

object PreferenceHelper {

    private const val PreferenceName = "LoyaltyPreferenceHelper_GP2020"

    private const val DashboardDetails = "LoyaltyPreferenceHelper_GP2020_uusAppOpenTodaysuu"

    private const val LoginDetails = "LoyaltyPreferenceHelper_GP2020_loginDetails"

    private const val SEDashboardDetails = "LoyaltyPreferenceHelper_uusDashboardDetail3suu"

    fun clear(context: Context){
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()
    }

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

    private fun jsonAdapter(): JsonAdapter<LoginResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(LoginResponse::class.java)
    }

    private fun jsonDashboardAdapter(): JsonAdapter<UpdatedDashboardSingleApiResponse> {
        val moshi = Moshi.Builder().build()
        return moshi.adapter(UpdatedDashboardSingleApiResponse::class.java)
    }

    fun setLoginDetails(context: Context, loginResponse: LoginResponse){
        val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
        val json = jsonAdapter.toJson(loginResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(LoginDetails, json).apply()
    }

    fun getLoginDetails(context: Context) : LoginResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(LoginDetails, "")

        val jsonAdapter: JsonAdapter<LoginResponse> = jsonAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }

    fun setDashboardDetails(context: Context, UpdatedDashboardSingleApiResponse: UpdatedDashboardSingleApiResponse){
        val jsonAdapter: JsonAdapter<UpdatedDashboardSingleApiResponse> = jsonDashboardAdapter()
        val json = jsonAdapter.toJson(UpdatedDashboardSingleApiResponse)

        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(DashboardDetails, json).apply()
    }

    fun getDashboardDetails(context: Context) : UpdatedDashboardSingleApiResponse? {
        val sharedPreferences = context.getSharedPreferences(PreferenceName, Context.MODE_PRIVATE)
        val stringValue = sharedPreferences.getString(DashboardDetails, "")

        val jsonAdapter: JsonAdapter<UpdatedDashboardSingleApiResponse> = jsonDashboardAdapter()
        return if(!stringValue.isNullOrEmpty()) {
            jsonAdapter.fromJson(stringValue)
        }else{
            null
        }
    }


}