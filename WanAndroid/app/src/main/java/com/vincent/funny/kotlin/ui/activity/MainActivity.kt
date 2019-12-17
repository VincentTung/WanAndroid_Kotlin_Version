package com.vincent.funny.kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.adapter.MainTabAdapter
import com.vincent.funny.kotlin.ui.fragment.KnowledgeTreeFragment
import com.vincent.funny.kotlin.ui.fragment.MainPageFragment
import com.vincent.funny.kotlin.ui.fragment.WXAccountFragment
import com.vincent.funny.kotlin.util.exView
import com.vincent.funny.kotlin.util.logd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wx_account.*
import kotlinx.android.synthetic.main.include_top_title.*
import com.vincent.funny.kotlin.ui.fragment.ProjectFragment


class MainActivity : BaseActivity() {

    private var fragmentList = mutableListOf<Fragment>()

    private val tabTexts = arrayOf("首页", "知识体系", "公众号", "项目")
    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.startMethodTracing("mainactivity")
        logd("***onCreate")
        logd("***onCreate_Restore:" + savedInstanceState?.getLong("time"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exView<ImageView>(R.id.img_back).visibility = View.INVISIBLE

        tv_title.visibility
        if ( fragmentList.isEmpty()) {
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
        Debug.stopMethodTracing()
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
            val index = savedInstanceState.getInt("index", -1)
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

