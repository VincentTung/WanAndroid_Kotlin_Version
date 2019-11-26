package com.example.myapplication.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.ArticleListAdapter
import com.example.myapplication.cfg.BANNER_OFFSET_TIME
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.entity.ResultData
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.ui.activity.WebViewActivity
import com.example.myapplication.util.BannerImageLoader
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil.schdulesTransform
import com.example.myapplication.util.LoadingState
import com.example.myapplication.util.LoadingState.LOADING_BEGIN
import com.example.myapplication.viewmodels.MainPageViewModel
import com.example.myapplication.viewmodels.MainPageViewModelFactory
import com.jcodecraeer.xrecyclerview.XRecyclerView
import com.uber.autodispose.autoDisposable
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import kotlinx.android.synthetic.main.item_knowledge_tree.*


/**
 * 首页
 */
class MainPageFragment : BaseFragment(), ArticleListAdapter.OnItemListener {


    private lateinit var mInflater: LayoutInflater
    private lateinit var recyclerview: XRecyclerView
    private lateinit var mViewModel: MainPageViewModel
    private var mAdapter: ArticleListAdapter = ArticleListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mViewModel =
            ViewModelProviders.of(this, MainPageViewModelFactory(BannerRepository()))
                .get(MainPageViewModel::class.java)
        mInflater = inflater
        var contentView = inflater.inflate(R.layout.fragment_main_page, null, false)
        recyclerview = contentView.findViewById<XRecyclerView>(R.id.recyclerView)
        recyclerview.addItemDecoration(
            DividerItemDecoration(
                this@MainPageFragment.context,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerview.setHasFixedSize(true)

        mViewModel.observeArticles().observe(this, Observer<PagedList<Article>> {
            mAdapter.submitList(it)
        })

        mAdapter.onItemListener = this
        recyclerview.adapter = mAdapter


        mViewModel.observeLoadingState().observe(this, Observer {

            if (it === LOADING_BEGIN) {
                recyclerview.refresh()
            } else {
                recyclerview.refreshComplete()
            }
        })



        mViewModel.observeBanners().observe(this, Observer {
            var imgList = it.map { it.imagePath }
            addBannerView(imgList, it)

        })

        recyclerview.setLoadingListener(object : XRecyclerView.LoadingListener {

            override fun onRefresh() {

                /**
                 * pagedlist 如何下拉刷新
                 */
                recyclerview.refreshComplete()

            }

            override fun onLoadMore() {
            }
        })

        return contentView
    }


    private fun getBanner() {

        ApiHelper.getInstance().getApiService().getBanner().compose(schdulesTransform())
            .autoDisposable(scopeProvider)
            .subscribe(object :
                BaseObserver<List<Banner>, ResultData<List<Banner>>> {
                override fun onFailed(errorCode: Int) {
                }

                override fun onSuccess(result: ResultData<List<Banner>>) {

                }


            })
    }

    private fun addBannerView(imgList: List<String?>?, banner:List<Banner>) {


        var bannerView = mInflater.inflate(R.layout.include_banner, null, false)
        var banner_head = bannerView.findViewById<com.youth.banner.Banner>(R.id.banner)

        banner_head.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner_head.setImageLoader(BannerImageLoader())

        banner_head.setBannerAnimation(Transformer.DepthPage)
        banner_head.isAutoPlay(true)
        banner_head.setDelayTime(BANNER_OFFSET_TIME)

        banner_head.setIndicatorGravity(BannerConfig.CENTER)
        recyclerview.addHeaderView(bannerView)

        banner_head.setImages(imgList)
        banner_head.setOnBannerListener {
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
        banner_head.start()
    }


    private fun stopLoad() {
//        if (mPage == 1) {
//            recyclerview.refreshComplete()
//        } else {
//            recyclerview.loadMoreComplete()
//        }
    }

    override fun onItemClick(position: Int) {

//        this@MainPageFragment.context?.let { it ->
//            mArticles[position].link?.let { it1 ->
//                WebViewActivity.start(
//                    this@MainPageFragment.context!!,
//                    it1
//                )
//            }
//        }
    }


}