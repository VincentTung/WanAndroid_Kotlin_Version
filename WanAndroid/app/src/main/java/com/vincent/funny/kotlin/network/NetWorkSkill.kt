package com.vincent.funny.kotlin.network


import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo  

class NetWorkSkill {

    companion object {
        fun isNetConnected(context: Context): Boolean {
            val connectivityManager: ConnectivityManager =
                context.applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetWorkInfo: NetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetWorkInfo != null && activeNetWorkInfo.isConnectedOrConnecting

        }

    }
}