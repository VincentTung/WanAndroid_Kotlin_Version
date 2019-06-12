package com.example.myapplication.view

import android.content.Context
import android.view.MotionEvent
import android.text.method.Touch.onTouchEvent
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager


class NoScrollViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isPagingEnabled = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return this.isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(b: Boolean) {
        this.isPagingEnabled = b
    }
}