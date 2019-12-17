package com.vincent.funny.kotlin.util

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun <T : View> Activity.exView(@IdRes id: Int): T {
    return findViewById(id)
}

fun <T : ViewModel> Fragment.exViewModel(modelClass: Class<T>): T {
    return ViewModelProviders.of(this).get(modelClass)
}

fun <T : ViewModel> Fragment.exViewModel(
    factory: ViewModelProvider.Factory,
    modelClass: Class<T>
): T {
    return ViewModelProviders.of(this, factory)
        .get(modelClass)
}

fun Activity.logd(text: String) {
    Log.d(this.javaClass.simpleName, text)
}


fun Activity.loge(text: String) {
    Log.e(this.javaClass.simpleName, text)
}


fun Activity.logw(text: String) {
    Log.w(this.javaClass.simpleName, text)
}


fun Activity.logi(text: String) {
    Log.i(this.javaClass.simpleName, text)
}


fun Fragment.logd(text: String) {
    Log.d(this.javaClass.simpleName, text)
}


fun Fragment.loge(text: String) {
    Log.e(this.javaClass.simpleName, text)
}


fun Fragment.logw(text: String) {
    Log.w(this.javaClass.simpleName, text)
}