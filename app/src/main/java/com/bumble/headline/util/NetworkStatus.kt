package com.wattpad.headlines.util

import android.content.Context
import android.net.ConnectivityManager

class NetworkStatus {

    fun isDataAvailable(ctx: Context): Boolean {

        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return let {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnected
        }
    }
}