package com.example.myapplication.util

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T : View> Activity.exView(@IdRes id: Int): T {
    return findViewById(id)
}

fun <T : ViewModel> Fragment.exViewModel(modelClass: Class<T>): T {
   return ViewModelProviders.of(this).get(modelClass)
}

fun <T : ViewModel> Fragment.exViewModel(factory:ViewModelProvider.Factory,modelClass: Class<T>): T {
    return  ViewModelProviders.of(this, factory)
        .get(modelClass)
}
