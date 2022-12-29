package com.loyaltyworks.keshavcement.utils.internet

interface ConnectivityReceiverListener {
    fun onNetworkConnectionChanged(isConnected: Boolean)
}