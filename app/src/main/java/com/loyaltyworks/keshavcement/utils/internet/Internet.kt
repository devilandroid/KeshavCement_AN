package com.loyaltyworks.keshavcement.utils.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.StrictMode
import com.loyaltyworks.keshavcement.ApplicationClass

object Internet {

    fun isNetworkConnected(): Boolean {
        val connectivityMgr = ApplicationClass.appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT > 9) {
            val gfgPolicy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(gfgPolicy)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return connectivityMgr.getNetworkCapabilities(connectivityMgr.activeNetwork)!=null

        } else {

            for (network in connectivityMgr.allNetworks) { // added in API 21 (Lollipop)
                val networkCapabilities: NetworkCapabilities? =
                    connectivityMgr.getNetworkCapabilities(network)
                return (networkCapabilities!!.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                        (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)))
            }
        }
        return false
    }



}