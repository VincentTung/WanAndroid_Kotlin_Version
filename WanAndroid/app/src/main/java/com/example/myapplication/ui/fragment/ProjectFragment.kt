package ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.R
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.entity.Chapter
import com.example.myapplication.entity.Project
import com.example.myapplication.entity.Tree
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.ui.fragment.BaseFragment
import com.example.myapplication.ui.fragment.ProjectSubFragment
import com.example.myapplication.ui.fragment.WXAccountSubFragment
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView


/**
 * 项目
 */
class ProjectFragment() : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(R.layout.fragment_wx_account, null, false)
        var magicIndicator = contentView.findViewById<MagicIndicator>(R.id.magic_indicator)
        var viewPager = contentView.findViewById<ViewPager>(R.id.viewpager)
        ApiHelper.mInstance.getApiService().getProjectTree().compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<List<Tree>, ResultData<List<Tree>>> {
                override fun onFailed(errorCode: Int) {

                }

                override fun onSuccess(t: ResultData<List<Tree>>) {

                    var fragments =
                        t.data?.map {
                            ProjectSubFragment(it.id)
                        }
                    var datas = t.data
                    magicIndicator.setBackgroundColor(resources.getColor(R.color.white))
                    val commonNavigator = CommonNavigator(this@ProjectFragment.context)
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
                                resources.getColor(R.color.black)
                            simplePagerTitleView.selectedColor =
                                resources.getColor(R.color.base_color)
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
                            indicator.setColors(resources.getColor(R.color.base_color))
                            return indicator
                        }
                    }

                    magicIndicator.navigator = commonNavigator
                    commonNavigator.onPageSelected(0)
                    ViewPagerHelper.bind(magicIndicator, viewPager)
                    viewPager.adapter = fragments?.let { MainTabAdapter(childFragmentManager, it) }

                }
            })
        return contentView
    }


}