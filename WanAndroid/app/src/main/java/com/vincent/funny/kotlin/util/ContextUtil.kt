package com.vincent.funny.kotlin.util

import android.app.Application
import android.content.Context


object ContextUtil {

    private var mApplication: Application? = null
    val context: Context
        get() = mApplication!!.applicationContext

    fun init(application: Application) {
        mApplication = application
    }
}
