package com.vincent.funny.kotlin.provider

import androidx.core.content.FileProvider
import com.vicnent.lib.base.util.ContextUtil
import android.app.Application

class InitProvider : FileProvider() {
    override fun onCreate(): Boolean {
        if (context is Application) {
            ContextUtil.init(context as Application)
        }
        return super.onCreate()
    }
}