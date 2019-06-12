package com.example.myapplication.util

import android.app.Activity
import android.util.Log
import androidx.fragment.app.Fragment


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


fun Fragment.logi(text: String) {
    Log.i(this.javaClass.simpleName, text)
}