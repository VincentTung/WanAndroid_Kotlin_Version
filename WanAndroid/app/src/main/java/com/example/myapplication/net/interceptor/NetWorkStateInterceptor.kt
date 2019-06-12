package com.example.myapplication.net.interceptor

import android.os.Handler
import com.example.myapplication.app.WanApplication
import com.example.myapplication.network.LiveNetWorkMonitor
import com.example.myapplication.network.NetWorkMonitor
import com.example.myapplication.util.toast
import okhttp3.Interceptor
import okhttp3.Response


class NetWorkStateInterceptor : Interceptor {
    private var mHandler: Handler = Handler(WanApplication.mInstance.mainLooper)
    private var mMonitor: NetWorkMonitor = LiveNetWorkMonitor(WanApplication.mInstance)

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!mMonitor.isConnected) {
            mHandler.post { WanApplication.mInstance.toast("没有网络") }
        }

        return chain.proceed(chain.request())
    }

}