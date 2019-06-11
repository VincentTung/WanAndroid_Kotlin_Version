package com.example.myapplication.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity

open class BaseActivity:FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onDestroy() {
        super.onDestroy()
    }

}