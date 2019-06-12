package com.example.myapplication.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.entity.Chapter
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_wx_account.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


/**
 * 公众号
 */
class WXAccountFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(com.example.myapplication.R.layout.fragment_wx_account, null, false)

        getTree()
        return contentView
    }

    private fun getTree() {
        ApiHelper.mInstance.getApiService().getChapterData().compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<List<Chapter>, ResultData<List<Chapter>>> {
                override fun onFailed(errorCode: Int) {

                }

                override fun onSuccess(t: ResultData<List<Chapter>>) {

                    var fragments =
                        t.data?.map {
                            WXAccountSubFragment(it.name, it.id)
                        }
                    var datas = t.data
                    magic_indicator.setBackgroundColor(resources.getColor(com.example.myapplication.R.color.white))
                    val commonNavigator = CommonNavigator(this@WXAccountFragment.context)
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
                                viewpager.currentItem = index
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
                    ViewPagerHelper.bind(magic_indicator, viewpager)
                    viewpager.adapter = fragments?.let { MainTabAdapter(childFragmentManager, it) }

                }
            })
    }


}