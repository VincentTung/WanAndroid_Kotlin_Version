package com.vincent.funny.kotlin.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open class BaseActivity:FragmentActivity() {
    val scopeProvider by lazy { AndroidLifecycleScopeProvider.from(lifecycle) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}