package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.datasource.ArticleDataSourceFactory
import com.example.myapplication.entity.Article
import java.util.concurrent.Executors
import androidx.lifecycle.Transformations
import com.example.myapplication.entity.Banner
import com.example.myapplication.repository.BannerRepository

import com.example.myapplication.util.LoadingState


class MainPageViewModel internal constructor(bannerRepository: BannerRepository) :
    ViewModel() {


    private val mArticles: LiveData<PagedList<Article>>
    private var mLoadingState: LiveData<LoadingState>
    private val mBanners: MediatorLiveData<List<Banner>> = MediatorLiveData()
    private  var mArticleDataFactory: ArticleDataSourceFactory = ArticleDataSourceFactory()

    init {
        val executor = Executors.newFixedThreadPool(5)
        val pagedListConfig: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(10)
                .setPageSize(20).build()
        mArticles =
            LivePagedListBuilder(mArticleDataFactory, pagedListConfig).setFetchExecutor(
                executor
            ).build()

        mLoadingState = Transformations.switchMap(
            mArticleDataFactory.observeArticleDataSource()
        )
        {

            it.observeLoadingState()

        }

        val source = bannerRepository.getBanners()
        mBanners.addSource(source) {
            mBanners.value = it.data
            mBanners.removeSource(source)
        }

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

}