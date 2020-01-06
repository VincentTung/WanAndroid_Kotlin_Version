package com.vincent.funny.kotlin.app

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.multidex.MultiDex
import com.vincent.funny.kotlin.BuildConfig
import com.vincent.lib.imagecontroller.ImageController

class WanApplication : Application() {

    companion object {
        lateinit var mInstance: WanApplication
            private set

    }
    override fun onCreate() {
        mInstance = this
        //open strict_mode
        if(BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build())
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build())
        }
        super.onCreate()
        ImageController.getInstance().setGlideLoaderAndInit()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(baseContext)
    }
}