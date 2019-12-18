package com.vincent.funny.kotlin.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.vincent.funny.kotlin.R
import com.vincent.funny.kotlin.adapter.ArticlePagedListAdapter
import com.vincent.funny.kotlin.cfg.BANNER_OFFSET_TIME
import com.vincent.funny.kotlin.entity.Article
import com.vincent.funny.kotlin.entity.Banner
import com.vincent.funny.kotlin.repository.BannerRepository
import com.vincent.funny.kotlin.ui.activity.WebViewActivity
import com.vincent.funny.kotlin.util.BannerImageLoader
import com.vincent.funny.kotlin.util.LoadingState
import com.vicnent.lib.base.util.exViewModel
import com.vincent.funny.kotlin.viewmodels.MainPageViewModel
import com.vincent.funny.kotlin.viewmodels.MainPageViewModelFactory
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.vincent.lib.imagecontroller.ImageController
import com.vincent.lib.imagecontroller.ImageLoader
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

        imgList?.let {
            this@MainPageFragment.context?.let { ctx ->
                imgList[1]?.let { url ->
                    ImageController.getInstance()
                        .download(ctx, url, object : ImageLoader.DownloadImageListener {
                            override fun onDownloadImageFinish(downloadPath: String?) {

                            }
                        })
                }
            }
        }
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