package com.vincent.funny.kotlin.net.interceptor

import android.os.Handler
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.app.WanApplication
import com.vincent.funny.kotlin.network.LiveNetWorkMonitor
import com.vincent.funny.kotlin.network.NetWorkMonitor
import com.vicnent.lib.base.util.ContextUtil
import com.vicnent.lib.base.util.toast
import okhttp3.Interceptor
import okhttp3.Response


class NetWorkStateInterceptor : Interceptor {
    private var mHandler: Handler = Handler(WanApplication.mInstance.mainLooper)
    private var mMonitor: NetWorkMonitor = LiveNetWorkMonitor(WanApplication.mInstance)

    override fun intercept(chain: Interceptor.Chain): Response {

        if (!mMonitor.isConnected()) {
            mHandler.post { ContextUtil.context.toast(R.string.no_initernet) }
        }

        return chain.proceed(chain.request())
    }

}