package com.vincent.funny.kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.funny.kotlin.cfg.EXECUTOR_SIZE
import com.vincent.funny.kotlin.cfg.PAGE_ITEMS_SIZE
import com.vincent.funny.kotlin.datasource.ArticleDataSourceFactory
import com.vincent.funny.kotlin.entity.Article
import com.vincent.funny.kotlin.entity.Banner
import com.vincent.funny.kotlin.repository.BannerRepository
import com.vincent.funny.kotlin.util.LoadingState
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