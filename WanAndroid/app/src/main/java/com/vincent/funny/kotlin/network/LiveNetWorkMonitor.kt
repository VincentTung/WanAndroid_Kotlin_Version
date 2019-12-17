package com.vincent.funny.kotlin.network

import android.content.Context
class LiveNetWorkMonitor(private val applicationContext: Context) : NetWorkMonitor {
    override fun isConnected(): Boolean {
        return NetWorkSkill.isNetConnected(applicationContext)
    }

}