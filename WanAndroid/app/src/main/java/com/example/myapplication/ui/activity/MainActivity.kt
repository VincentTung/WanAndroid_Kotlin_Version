package com.example.myapplication.ui.activity

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
import com.example.myapplication.util.logd
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_knowledge_tree.*
import ui.fragment.ProjectFragment


class MainActivity : BaseActivity() {

    private lateinit var fragmentList: List<Fragment>

    private lateinit var tv_title: TextView
    private val tabTexts = arrayOf("首页", "知识体系", "公众号", "项目")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ImageView>(R.id.img_back).visibility = View.INVISIBLE
        tv_title = findViewById(R.id.tv_title)
        fragmentList = listOf(
            MainPageFragment(), KnowledgeTreeFragment(), WXAccountFragment(), ProjectFragment()
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

