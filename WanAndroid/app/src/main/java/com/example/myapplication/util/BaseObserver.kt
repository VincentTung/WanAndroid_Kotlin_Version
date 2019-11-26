package com.example.myapplication.util

import android.util.Log
import com.example.myapplication.app.WanApplication
import com.example.myapplication.cfg.ERROR_CODE_EXCEPTION
import com.example.myapplication.entity.ResultData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable


interface BaseObserver<R,T : ResultData<R>> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
    }

    override fun onError(e: Throwable) {
        Log.e("BaseObserver", e.toString())
        onFailed(ERROR_CODE_EXCEPTION)
    }

    override fun onComplete() {
    }

    override fun onNext(result: T) {
        if (result.errorCode == 0) {
            onSuccess(result)
        } else {
            result.errorMsg?.let { WanApplication.mInstance.toast(it) }
            onFailed(result.errorCode)
        }
    }

    fun onFailed(errorCode: Int)
    fun onSuccess(t: T)


}