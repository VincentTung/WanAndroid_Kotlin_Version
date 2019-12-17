package com.vincent.funny.kotlin.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.adapter.MainTabAdapter
import com.vincent.funny.kotlin.entity.Tree
import com.vincent.funny.kotlin.ui.fragment.WXAccountSubFragment
import com.vincent.funny.kotlin.util.exView
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
        fun start(context: Context, tree: Tree) {
            val intent = Intent(context, KnowledgeTreeActivity::class.java)
            intent.putExtra("tree", tree)
            context.startActivity(intent)
        }
    }

    private lateinit var tv_title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knowledge_tree)
        tv_title = exView(R.id.tv_title)
        exView<ImageView>(R.id.img_back).setOnClickListener { finish() }
        val tree: Tree = intent.getParcelableExtra("tree")
        tv_title.text = tree.name


        val fragments =
            tree.children?.map {
                WXAccountSubFragment.newInstance(it.id)
            }
         val datas = tree.children

        magic_indicator.apply {
            setBackgroundColor(resources.getColor(com.vincent.funny.kotlin.R.color.white))
            navigator = CommonNavigator(this@KnowledgeTreeActivity).apply {
                isAdjustMode = false
                scrollPivotX = 0.25f
                adapter = object : CommonNavigatorAdapter() {
                    override fun getCount(): Int {
                        return datas?.size ?: 0
                    }

                    override fun getTitleView(context: Context, index: Int): IPagerTitleView {

                        return SimplePagerTitleView(context).apply {

                            text = datas?.get(index)?.name
                            normalColor =
                                resources.getColor(com.vincent.funny.kotlin.R.color.black)
                            selectedColor =
                                resources.getColor(com.vincent.funny.kotlin.R.color.base_color)
                            textSize = 15f
                            val paddingLeft = UIUtil.dip2px(context, 20.0)
                            val paddingTop = UIUtil.dip2px(context, 8.0)
                            setPadding(
                                paddingLeft,
                                paddingTop,
                                paddingLeft,
                                paddingTop
                            )
                            setOnClickListener {
                                viewPager.currentItem = index
                            }
                        }
                    }

                    override fun getIndicator(context: Context): IPagerIndicator {

                        return LinePagerIndicator(context).apply {
                            mode = LinePagerIndicator.MODE_EXACTLY
                            lineWidth = UIUtil.dip2px(context, 20.0).toFloat()
                            lineHeight = UIUtil.dip2px(context, 2.0).toFloat()
                            setColors(resources.getColor(com.vincent.funny.kotlin.R.color.base_color))
                        }
                    }
                }
                onPageSelected(0)
            }
        }
        ViewPagerHelper.bind(magic_indicator, viewPager)
        viewPager.adapter = fragments?.let { MainTabAdapter(supportFragmentManager, it) }


    }
}
