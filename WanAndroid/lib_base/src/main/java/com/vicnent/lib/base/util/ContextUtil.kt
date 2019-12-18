package com.vicnent.lib.base.util

import android.app.Application
import android.content.Context


object ContextUtil {

    private var mApplication: Application? = null

    fun init(application: Application) {
        mApplication = application
    }

    fun getContext(): Context? {
        mApplication?.let {
          return   mApplication!!.applicationContext
        }
        return null
    }
}
