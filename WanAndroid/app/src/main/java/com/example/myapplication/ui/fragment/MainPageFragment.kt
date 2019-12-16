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
import com.example.myapplication.adapter.ArticlePagedListAdapter
import com.example.myapplication.cfg.BANNER_OFFSET_TIME
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.example.myapplication.util.LoadingState
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
    private var mAdapter = ArticlePagedListAdapter()
    private val mPreLoadPage = 2
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
                    recyclerView.refreshComplete()
                }

                override fun onLoadMore() {
                }
            })
        }
        mViewModel.observeBanners().observe(this, Observer { banners ->
            val imgList = banners.map { banner -> banner.imagePath }
            addBannerView(imgList, banners)

        })
        mViewModel.observeArticles().observe(this, Observer<PagedList<Article>> {
            mAdapter.submitList(it)
        })

        mViewModel.observeLoadingState().observe(this, Observer {
            if (it == LoadingState.LOADING_STOP) {
                loading_view.visibility = View.INVISIBLE
                loading_view.stopAnim()
            } else {
                loading_view.visibility = View.VISIBLE
                loading_view.startAnim()
            }
        })

        mViewModel.observePage().observe(this, Observer { page ->
            if (page <= mPreLoadPage) {
                recyclerView.scrollToPosition(0)
            }
        })


    }

    private fun addBannerView(imgList: List<String?>?, banner: List<Banner>) {

        val bannerView = LayoutInflater.from(context).inflate(R.layout.include_banner, null, false)
        bannerView.findViewById<com.youth.banner.Banner>(R.id.banner).apply {
            setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            setImageLoader(BannerImageLoader())
            setBannerAnimation(Transformer.DepthPage)
            isAutoPlay(true)
            setDelayTime(BANNER_OFFSET_TIME)
            setIndicatorGravity(BannerConfig.CENTER)
            setImages(imgList)
            setOnBannerListener {
                val url = banner[it].url
                this@MainPageFragment.context?.let { context ->
                    url?.let { url ->
                        WebViewActivity.start(
                            context,
                            url
                        )
                    }
                }
            }
        }.start()
        recyclerView.addHeaderView(bannerView)
    }
}