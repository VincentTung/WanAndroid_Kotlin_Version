package com.vincent.funny.kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.cfg.TIME_SPLASH_DELAY
import com.uber.autodispose.android.lifecycle.scope
import com.uber.autodispose.autoDisposable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Observable.timer(TIME_SPLASH_DELAY, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io())
            .unsubscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()
            ).autoDisposable(lifecycle.scope()).subscribe {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                overridePendingTransition(R.anim.anim_zoom_in,R.anim.anim_zoom_out)
                finish()

            }
    }
}
