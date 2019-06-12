package com.example.myapplication.ui.fragment

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.PageData
import com.example.myapplication.entity.Banner
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.uber.autodispose.autoDisposable
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_main_page.*


/**
 * 首页
 */
class MainPageFragment : BaseFragment(), ArticleListAdapter.OnItemListener {


    private val banner_offset_time = 2000
    private var mHandler: Handler = Handler()
    private var mPage = 0
    private var mArticles: MutableList<Article> = mutableListOf()
    private var mAdapter: ArticleListAdapter = ArticleListAdapter(mArticles)
    private lateinit var mNestedScrollView: NestedScrollView
    private lateinit var mProgressBar: ProgressBar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var contentView = inflater.inflate(R.layout.fragment_main_page, null, false)
        mNestedScrollView =
            contentView.findViewById(R.id.nestedscrollview)

        mProgressBar = contentView.findViewById(R.id.progressbar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mNestedScrollView
                .setOnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, ioldScrollY3: Int ->
                    if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                        getArticle(mPage + 1)
                    }

                }
        }


        var recyclerview = contentView.findViewById<RecyclerView>(R.id.recyclerView)
        var linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainPageFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerview.layoutManager = linearLayoutManager

        mAdapter.onItemListener = this
        recyclerview.adapter = mAdapter
        recyclerview.setHasFixedSize(true)
        getBanner()
        getArticle(mPage)

        return contentView
    }

    private fun getBanner() {

        ApiHelper.mInstance.getApiService().getBanner().compose(schdulesTransform()).autoDisposable(scopeProvider)
            .subscribe(object :
                BaseObserver<List<Banner>, ResultData<List<Banner>>> {
                override fun onFailed(errorCode: Int) {
                }

                override fun onSuccess(result: ResultData<List<Banner>>) {
                    var imgList = result.data?.map { it.imagePath }
                    banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                    banner.setImageLoader(BannerImageLoader())
                    banner.setImages(imgList)
                    banner.setBannerAnimation(Transformer.DepthPage)
                    banner.isAutoPlay(true)
                    banner.setDelayTime(banner_offset_time)
                    banner.setOnBannerListener {
                        var url = result.data?.get(it)?.url
                        this@MainPageFragment.context?.let { it1 ->
                            url?.let { it2 ->
                                WebViewActivity.start(
                                    it1,
                                    it2
                                )
                            }
                        }

                    }
                    banner.setIndicatorGravity(BannerConfig.CENTER)

                    banner.start()
                }


            })
    }


    private fun getArticle(page: Int) {

        mProgressBar.visibility = View.VISIBLE
        ApiHelper.mInstance.getApiService().getArticle(page).compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<PageData<List<Article>>, ResultData<PageData<List<Article>>>> {
                override fun onSuccess(t: ResultData<PageData<List<Article>>>) {
                    if (t.errorCode == 0) {
                        t.data?.datas?.let {
                            if (t.data?.datas!!.isNotEmpty()) {
                                mPage = t.data!!.curPage
                                mArticles.addAll(it)
                                mAdapter.notifyDataSetChanged()
                                if (mPage != 1) {
                                    mHandler.postDelayed({ mNestedScrollView.smoothScrollBy(0, 200) }, 100)
                                }
                            }
                        }
                    }
                    mProgressBar.visibility = View.GONE
                }

                override fun onFailed(errorCode: Int) {
                    mProgressBar.visibility = View.GONE
                }


            })
    }

    override fun onItemClick(position: Int) {

        this@MainPageFragment.context?.let { it ->
            mArticles[position].link?.let { it1 ->
                WebViewActivity.start(
                    this@MainPageFragment.context!!,
                    it1
                )
            }
        }
    }


}