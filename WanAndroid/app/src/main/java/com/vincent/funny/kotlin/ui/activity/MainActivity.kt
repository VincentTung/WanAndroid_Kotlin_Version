package com.vincent.funny.kotlin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.ui.fragment.KnowledgeTreeFragment
import com.vincent.funny.kotlin.ui.fragment.MainPageFragment
import com.vincent.funny.kotlin.ui.fragment.WXAccountFragment
import com.vicnent.lib.base.util.exView
import com.vicnent.lib.base.util.logd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_wx_account.*
import kotlinx.android.synthetic.main.include_top_title.*
import com.vincent.funny.kotlin.ui.fragment.ProjectFragment


class MainActivity : BaseActivity() {

    private var fragmentList = mutableListOf<Fragment>()
    private var mCurrentTabIndex = -1
    private val mFragments: Array<Class<out Fragment?>> = arrayOf(MainPageFragment::class.java, KnowledgeTreeFragment::class.java, WXAccountFragment::class.java, ProjectFragment::class.java)
    private var mCurrentFragment: Fragment? = null
    private val tabTexts = arrayOf("首页", "知识体系", "公众号", "项目")
    override fun onCreate(savedInstanceState: Bundle?) {
        Debug.startMethodTracing("mainactivity")
        logd("***onCreate")
        logd("***onCreate_Restore:" + savedInstanceState?.getLong("time"))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        exView<ImageView>(R.id.img_back).visibility = View.INVISIBLE

        tv_title.visibility
        if (fragmentList.isEmpty()) {
            fragmentList.addAll(
                listOf(
                    MainPageFragment(), KnowledgeTreeFragment(),
                    WXAccountFragment(),
                    ProjectFragment()
                )
            )

            alphaIndicator.apply {
                setOnTabChangedListner {
                    tv_title.text = tabTexts[it]
                    changeTab(it)
                }
            }
        }

        changeTab(0)
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


    private fun changeTab(position: Int) {

        if (mCurrentTabIndex == position) return
        val tag = position.toString()
        val transaction = supportFragmentManager.beginTransaction()
        var fragment = supportFragmentManager.findFragmentByTag(tag)

        if (fragment == null) {
            mCurrentFragment ?.run {
                transaction.hide(this)
            }
            fragment = mFragments[position].newInstance()
            transaction.add(R.id.content, fragment!!, tag)
        } else {
            mCurrentFragment?.run {
                transaction.hide(this)
            }
            transaction.show(fragment)
        }
        mCurrentFragment = fragment
        transaction.commitAllowingStateLoss()
        mCurrentTabIndex = position
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        logd("***onSaveInstanceState")
        outState.putInt("index", mCurrentTabIndex)
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
            if (index > 0 && mCurrentTabIndex != index) {
                changeTab(index)
            }else if(index < 0){
                changeTab(0)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        logd("***onNewIntent")
    }


}

