package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.myapplication.R
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {
    private val TIME_SPLASH_DELAY = 1500L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_splash)

        Observable.timer(TIME_SPLASH_DELAY, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).autoDisposable(AndroidLifecycleScopeProvider.from(this)).subscribe { aLong ->

            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }
    }
}
