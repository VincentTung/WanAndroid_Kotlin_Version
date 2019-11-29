package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.cfg.BANNER_OFFSET_TIME
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.example.myapplication.util.LoadingState.LOADING_BEGIN
import com.example.myapplication.util.exViewModel
import com.example.myapplication.viewmodels.MainPageViewModel
import com.example.myapplication.viewmodels.MainPageViewModelFactory
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.fragment_main_page.*


/**
 * 首页
 */
class MainPageFragment : BaseFragment() {

    private lateinit var mViewModel: MainPageViewModel
    private var mAdapter: ArticleListAdapter = ArticleListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            exViewModel(MainPageViewModelFactory(BannerRepository()), MainPageViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main_page, null, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.apply {
            addItemDecoration(
                DividerItemDecoration(
                    this@MainPageFragment.context,
                    DividerItemDecoration.VERTICAL
                )
            )
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(true)
            adapter = mAdapter

            setLoadingListener(object : XRecyclerView.LoadingListener {
                override fun onRefresh() {
//                    mViewModel.observeArticles().value?.dataSource?.invalidate()
                    mViewModel.refreshArticles()

                }

                override fun onLoadMore() {
                }
            })
        }
        mViewModel.observeBanners().observe(this, Observer {
            var imgList = it.map { it.imagePath }
            addBannerView(imgList, it)

        })
        mViewModel.observeArticles().observe(this, Observer<PagedList<Article>> {
            mAdapter.submitList(it)
            //todo 首页 滑动到头部的问题
        })


    }

    private fun addBannerView(imgList: List<String?>?, banner: List<Banner>) {

        var bannerView = LayoutInflater.from(context).inflate(R.layout.include_banner, null, false)
        bannerView.findViewById<com.youth.banner.Banner>(R.id.banner).apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setImageLoader(BannerImageLoader())
            setBannerAnimation(Transformer.DepthPage)
            isAutoPlay(true)
            setDelayTime(BANNER_OFFSET_TIME)
            setIndicatorGravity(BannerConfig.CENTER)
            setImages(imgList)
            setOnBannerListener {
                var url = banner?.get(it)?.url
                this@MainPageFragment.context?.let { it1 ->
                    url?.let { it2 ->
                        WebViewActivity.start(
                            it1,
                            it2
                        )
                    }
                }

            }
        }.start()
        recyclerView.addHeaderView(bannerView)
    }


}