package com.vincent.funny.kotlin.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.vincent.lib.imagecontroller.ImageController

class WanApplication : Application() {

    companion object {
        lateinit var mInstance: WanApplication
            private set

    }

    override fun onCreate() {
        mInstance = this
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(baseContext)
//        ImageController.getInstance().setGlideLoaderAndInit()
        ImageController.getInstance().setPicassoLoaderAndInit()
    }
}