package ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.myapplication.R
import com.example.myapplication.adapter.MainTabAdapter
import com.example.myapplication.ui.fragment.BaseFragment
import com.example.myapplication.ui.fragment.ProjectSubFragment
import com.example.myapplication.util.exViewModel
import com.example.myapplication.viewmodels.ProjectViewModel
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
 * 项目
 */
class ProjectFragment : BaseFragment() {


    private lateinit var mViewModel:ProjectViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = exViewModel(ProjectViewModel::class.java)
        return  inflater.inflate(R.layout.fragment_wx_account, null, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.observeProjects().observe(this, Observer {

            var fragments =
                 it.map {
                    ProjectSubFragment.newInstance(it.id)
                }

            magic_indicator.setBackgroundColor(resources.getColor(R.color.white))
            magic_indicator.navigator = CommonNavigator(this@ProjectFragment.context).apply {

                isAdjustMode = false
                scrollPivotX = 0.25f
                adapter = object : CommonNavigatorAdapter() {
                    override fun getCount(): Int {
                        return it.size ?: 0
                    }

                    override fun getTitleView(
                        context: Context,
                        index: Int
                    ): IPagerTitleView {

                        return SimplePagerTitleView(context).apply {
                            text = it.get(index)?.name
                            normalColor =
                                resources.getColor(R.color.black)
                            selectedColor =
                                resources.getColor(R.color.base_color)
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
                                viewpager.currentItem = index
                            }
                        }
                    }

                    override fun getIndicator(context: Context): IPagerIndicator {
                        return LinePagerIndicator(context).apply {
                            mode = LinePagerIndicator.MODE_EXACTLY
                            lineWidth = UIUtil.dip2px(context, 20.0).toFloat()
                            lineHeight = UIUtil.dip2px(context, 2.0).toFloat()
                            setColors(resources.getColor(R.color.base_color))
                        }
                    }
                }
                onPageSelected(0)
            }
            ViewPagerHelper.bind(magic_indicator, viewpager)
            viewpager.adapter = fragments?.let { it -> MainTabAdapter(childFragmentManager, it) }
        })

        mViewModel.getProjects()

    }


}