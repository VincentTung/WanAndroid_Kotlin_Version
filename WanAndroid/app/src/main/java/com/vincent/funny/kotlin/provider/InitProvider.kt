package com.vincent.funny.kotlin.provider

import android.content.Context
import android.content.pm.ProviderInfo
import androidx.core.content.FileProvider
import com.vincent.funny.kotlin.util.ContextUtil
import android.app.Application
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class InitProvider : FileProvider() {
    override fun onCreate(): Boolean {
        if (context is Application) {
            ContextUtil.init(context as Application)
        }
        return super.onCreate()
    }
}