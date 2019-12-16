package com.example.myapplication.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.myapplication.R
import android.widget.LinearLayout
import com.example.myapplication.util.logd
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_webview.*


class WebViewActivity : BaseActivity() {

    companion object{
        @JvmStatic
        fun start(context: Context, url:String){
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        logd("***onCreate")
        setContentView(R.layout.activity_webview)
        iv_back.setOnClickListener {
            finish()
        }
        val url = intent.getStringExtra("url")
        AgentWeb.with(this)
            .setAgentWebParent(content, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }


    override fun onStart() {
        super.onStart()
        logd("***onStart")
    }

    override fun onResume() {
        super.onResume()
        logd("***onResume")
    }


    override fun onPause() {
        super.onPause()
        logd("***onPause")
    }

    override fun onStop() {
        super.onStop()
        logd("***onStop")
    }


    override fun onRestart() {
        super.onRestart()
        logd("***onRestart")
    }

    override fun onDestroy() {
        super.onDestroy()
        logd("***onDestroy")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        logd("***onRestoreInstanceState")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logd("***onNewIntent")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logd("***onSaveInstanceState")
    }
}