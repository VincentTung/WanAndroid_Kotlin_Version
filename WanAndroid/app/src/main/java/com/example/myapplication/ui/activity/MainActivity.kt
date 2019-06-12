package com.example.myapplication.ui.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.ui.fragment.KnowledgeTreeFragment
import com.example.myapplication.ui.fragment.MainPageFragment
import com.example.myapplication.ui.fragment.WXAccountFragment
import com.example.myapplication.util.logd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_knowledge_tree.*


class MainActivity : BaseActivity() {

    private lateinit var fragmentList: List<Fragment>
    private val tabTexts = arrayOf("首页", "知识体系", "公众号", "项目", "V2EX")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logd("onCreate")

        fragmentList = listOf(
            MainPageFragment(), KnowledgeTreeFragment(), WXAccountFragment(), MainPageFragment(), MainPageFragment()
        )

        viewPager.adapter = MainTabAdapter(supportFragmentManager, fragmentList)
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = 3
        viewPager.isEnabled = false

        alphaIndicator.setOnTabChangedListner {
            tv_title.text = tabTexts[it]
        }
        alphaIndicator.setViewPager(viewPager)


    }


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

