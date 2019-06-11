package com.example.myapplication.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.myapplication.R
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import kotlinx.android.synthetic.main.activity_webview.*


class WebViewActivity : BaseActivity() {



    companion object{

        @JvmStatic
        fun start(context: Context, url:String){
            var intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", url)
            context?.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        iv_back.setOnClickListener {
            finish()
        }
        var url = intent.getStringExtra("url")
        AgentWeb.with(this)
            .setAgentWebParent(content, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(url)
    }
}