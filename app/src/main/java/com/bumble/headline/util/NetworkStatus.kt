package com.bumble.headline.util

import android.content.Context
import android.net.ConnectivityManager

/**
 *
 *  NewtowkStatus
 *
 *  isDataAvailable - check if the data connection or internet available for the hardware device
 *
 */

class NetworkStatus {

    fun isDataAvailable(ctx: Context): Boolean {

        val connectivityManager = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return let {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork != null && activeNetwork.isConnected
        }
    }
}