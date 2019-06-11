package com.example.myapplication.app

import android.app.Application

class WanApplication : Application() {

    companion object {
        lateinit var mInstance: WanApplication
            private set

    }

    override fun onCreate() {
        mInstance = this
        super.onCreate()
    }
}