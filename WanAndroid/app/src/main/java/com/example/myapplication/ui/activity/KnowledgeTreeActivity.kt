package com.example.myapplication.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.entity.KnowledgeTree
import com.example.myapplication.ui.fragment.WXAccountSubFragment
import kotlinx.android.synthetic.main.activity_knowledge_tree.*

import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

class KnowledgeTreeActivity : BaseActivity() {

    companion object {

        @JvmStatic
        fun start(context: Context, tree: KnowledgeTree) {
            var intent = Intent(context, KnowledgeTreeActivity::class.java)
            intent.putExtra("tree", tree)
            context?.startActivity(intent)
        }
    }

    private lateinit var tv_title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_tree)
        tv_title = findViewById(R.id.tv_title)
        findViewById<ImageView>(R.id.img_back).setOnClickListener { finish() }
        var tree: KnowledgeTree = intent.getParcelableExtra("tree")

        tv_title.text = tree.name


        var fragments =
            tree.children?.map {
                WXAccountSubFragment(it.name, it.id)
            }
        var datas = tree.children
        magic_indicator.setBackgroundColor(resources.getColor(com.example.myapplication.R.color.white))
        val commonNavigator = CommonNavigator(this@KnowledgeTreeActivity)
        commonNavigator.isAdjustMode = false
        commonNavigator.scrollPivotX = 0.25f
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return datas?.size ?: 0
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                val simplePagerTitleView = SimplePagerTitleView(context)
                simplePagerTitleView.text = datas?.get(index)?.name
                simplePagerTitleView.normalColor =
                    resources.getColor(com.example.myapplication.R.color.black)
                simplePagerTitleView.selectedColor =
                    resources.getColor(com.example.myapplication.R.color.base_color)
                simplePagerTitleView.textSize = 15f
                val paddingLeft = UIUtil.dip2px(context, 20.0)
                val paddingTop = UIUtil.dip2px(context, 8.0)
                simplePagerTitleView.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop)

                simplePagerTitleView.setOnClickListener {
                    viewPager.currentItem = index
                }
                return simplePagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineWidth = UIUtil.dip2px(context, 20.0).toFloat()
                indicator.lineHeight = UIUtil.dip2px(context, 2.0).toFloat()
                indicator.setColors(resources.getColor(com.example.myapplication.R.color.base_color))
                return indicator
            }
        }

        magic_indicator.navigator = commonNavigator
        commonNavigator.onPageSelected(0)
        ViewPagerHelper.bind(magic_indicator, viewPager)
        viewPager.adapter = fragments?.let { MainTabAdapter(supportFragmentManager, it) }


    }
}
