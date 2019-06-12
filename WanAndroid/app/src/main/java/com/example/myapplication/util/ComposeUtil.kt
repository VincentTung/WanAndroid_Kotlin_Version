package com.example.myapplication.util

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object ComposeUtil {


    fun <T> schdulesTransform(observableScheduler: Scheduler = Schedulers.io(), observerScheduler: Scheduler = AndroidSchedulers.mainThread())
            : ObservableTransformer<T, T> = ObservableTransformer {
        it.subscribeOn(Schedulers.io())
            .unsubscribeOn(observableScheduler)
            .observeOn(observerScheduler)
    }
}
