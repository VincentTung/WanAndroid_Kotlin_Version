package com.example.myapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.ui.fragment.KnowledgeTreeFragment
import com.example.myapplication.ui.fragment.MainPageFragment
import com.example.myapplication.ui.fragment.WXAccountFragment
import com.example.myapplication.util.exView
import com.example.myapplication.util.logd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wx_account.*
import kotlinx.android.synthetic.main.include_top_title.*
import ui.fragment.ProjectFragment


class MainActivity : BaseActivity() {

    private var fragmentList = mutableListOf<Fragment>()

//    private lateinit var tv_title: TextView
    private val tabTexts = arrayOf("首页", "知识体系", "公众号", "项目")
    override fun onCreate(savedInstanceState: Bundle?) {
        logd("***onCreate")
        logd("***onCreate_Restore:" + savedInstanceState?.getLong("time"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exView<ImageView>(R.id.img_back).visibility = View.INVISIBLE
//        tv_title = exView(R.id.tv_title)

        tv_title.visibility
        if (fragmentList == null || fragmentList.isEmpty()) {
            fragmentList.addAll(
                listOf(
                    MainPageFragment()
                   , KnowledgeTreeFragment(),
                    WXAccountFragment(),
                    ProjectFragment()
                )
            )


            viewPager.apply {
                adapter = MainTabAdapter(supportFragmentManager, fragmentList)
                currentItem = 0
                offscreenPageLimit = 3
                isEnabled = false
            }

            alphaIndicator.apply {
                setOnTabChangedListner {
                    tv_title.text = tabTexts[it]
                }
                setViewPager(viewPager)
            }
        }

        restoreTabIndex(savedInstanceState)

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logd("***onSaveInstanceState")
        outState.putInt("index", viewpager.currentItem)
        outState.putLong("time", System.currentTimeMillis())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        logd("***onRestoreInstanceState")
        logd("***Restore" + savedInstanceState?.getLong("time"))
        restoreTabIndex(savedInstanceState)

    }

    private fun restoreTabIndex(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            var index = savedInstanceState.getInt("index", -1)
            if (index > 0 && viewpager.currentItem != index) {
                viewPager.currentItem = index
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logd("***onNewIntent")
    }


}

