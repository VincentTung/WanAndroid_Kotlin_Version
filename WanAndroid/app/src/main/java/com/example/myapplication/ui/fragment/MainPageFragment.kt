package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.entity.PageData
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.net.ResultData
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.uber.autodispose.autoDisposable
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer


/**
 * 首页
 */
class MainPageFragment : BaseFragment(), ArticleListAdapter.OnItemListener {


    private lateinit var mInflater: LayoutInflater
    private lateinit var recyclerview: XRecyclerView
    private val banner_offset_time = 2000
    private var mPage = 0
    private var mArticles: MutableList<Article> = mutableListOf()
    private var mAdapter: ArticleListAdapter = ArticleListAdapter(mArticles)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mInflater = inflater
        var contentView = inflater.inflate(R.layout.fragment_main_page, null, false)
        recyclerview = contentView.findViewById<XRecyclerView>(R.id.recyclerView)
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
        mAdapter.notifyDataSetChanged()
        recyclerview.setHasFixedSize(true)


        getBanner()
        getArticle(mPage)
        recyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {

            override fun onRefresh() {
                mArticles.clear()
                mAdapter.notifyDataSetChanged()
                mPage = 0
                getArticle(mPage)
            }

            override fun onLoadMore() {
                getArticle(mPage + 1)

            }
        })

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
                    addBannerView(imgList, result)
                }


            })
    }

    private fun addBannerView(imgList: List<String?>?, result: ResultData<List<Banner>>) {


        var bannerView = mInflater.inflate(R.layout.include_banner, null, false)
        var banner_head = bannerView.findViewById<com.youth.banner.Banner>(R.id.banner)

        banner_head.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner_head.setImageLoader(BannerImageLoader())

        banner_head.setBannerAnimation(Transformer.DepthPage)
        banner_head.isAutoPlay(true)
        banner_head.setDelayTime(banner_offset_time)

        banner_head.setIndicatorGravity(BannerConfig.CENTER)
        recyclerview.addHeaderView(bannerView)

        banner_head.setImages(imgList)
        banner_head.setOnBannerListener {
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
        banner_head.start()
    }


    private fun getArticle(page: Int) {

        ApiHelper.mInstance.getApiService().getArticle(page).compose(schdulesTransform())
            .autoDisposable(scopeProvider).subscribe(object :
                BaseObserver<PageData<List<Article>>, ResultData<PageData<List<Article>>>> {
                override fun onSuccess(t: ResultData<PageData<List<Article>>>) {
                    if (t.errorCode == 0) {
                        t.data?.datas?.let {
                            if (t.data?.datas!!.isNotEmpty()) {
                                mPage = t.data!!.curPage

                                if (mArticles.isEmpty()) {
                                    mArticles.addAll(it)
                                    mAdapter.notifyDataSetChanged()
                                } else {
                                    var begin = mArticles.size
                                    mArticles.addAll(it)
                                    mAdapter.notifyItemRangeInserted(begin, it.size)
                                }

                            }
                        }
                    }
                    stopLoad()
                }

                override fun onFailed(errorCode: Int) {
                    stopLoad()
                }


            })
    }

    private fun stopLoad() {
        if (mPage == 1) {
            recyclerview.refreshComplete()
        } else {
            recyclerview.loadMoreComplete()
        }
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