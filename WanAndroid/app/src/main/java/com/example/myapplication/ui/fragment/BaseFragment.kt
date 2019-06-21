package com.example.myapplication.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ComputableLiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Article
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider

open class BaseFragment:Fragment(){

     val scopeProvider: AndroidLifecycleScopeProvider by lazy { AndroidLifecycleScopeProvider.from(this) }
     override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

         return super.onCreateView(inflater, container, savedInstanceState)
     }
 }