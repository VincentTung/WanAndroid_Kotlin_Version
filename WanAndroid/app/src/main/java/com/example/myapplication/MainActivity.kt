package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.ui.activity.BaseActivity
import com.example.myapplication.ui.fragment.MainPageFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var fragmentList: List<Fragment>
    private  val TEXTS:Array<String> = arrayOf("首页","知识体系","公众号","项目","V2EX")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragmentList = listOf(
            MainPageFragment(), MainPageFragment(), MainPageFragment(), MainPageFragment(), MainPageFragment()
        )

        viewPager.adapter = MainTabAdapter(supportFragmentManager, fragmentList)
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = 3

        alphaIndicator.setOnTabChangedListner {
            tv_title.text = TEXTS[it]
        }


    }


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

