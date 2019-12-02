package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.cfg.EXECUTOR_SIZE
import com.example.myapplication.cfg.PAGE_ITEMS_SIZE
import com.example.myapplication.datasource.ArticleDataSourceFactory
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.util.LoadingState
import java.util.concurrent.Executors


class MainPageViewModel internal constructor(bannerRepository: BannerRepository) :
    ViewModel() {

    private var mArticles: LiveData<PagedList<Article>>
    private var mLoadingState: LiveData<LoadingState>
    private var mPage: LiveData<Int>
    private val mBanners: MediatorLiveData<List<Banner>> = MediatorLiveData()

    init {
        val factory = ArticleDataSourceFactory()
        val executor = Executors.newFixedThreadPool(EXECUTOR_SIZE)
        val pagedListConfig: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(
                PAGE_ITEMS_SIZE * 2
            )
                .setPageSize(PAGE_ITEMS_SIZE).build()
        mArticles =
            LivePagedListBuilder(factory, pagedListConfig).setFetchExecutor(
                executor
            ).build()

        mLoadingState = Transformations.switchMap(
            factory.observeArticleDataSource()
        )
        {
            it.observeLoadingState()
        }

        mPage = Transformations.switchMap(factory.observeArticleDataSource()) {
            it.observePage()
        }
        val source = bannerRepository.getBanners()
        mBanners.addSource(source) {
            mBanners.value = it.data
            mBanners.removeSource(source)
        }

    }

    /**
     * 刷新数据
     */
    fun refreshArticles() {
        mArticles.value?.dataSource?.invalidate()
    }

    fun observeArticles(): LiveData<PagedList<Article>> {
        return mArticles
    }

    fun observeLoadingState(): LiveData<LoadingState> {
        return mLoadingState
    }

    fun observeBanners(): LiveData<List<Banner>> {
        return mBanners
    }

    fun observePage(): LiveData<Int> {
        return mPage
    }

}