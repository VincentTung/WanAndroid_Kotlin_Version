package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.cfg.EXECUTOR_SIZE
import com.example.myapplication.cfg.PAGE_ITEMS_SIZE
import com.example.myapplication.datasource.ArticleDataSourceFactory
import com.example.myapplication.datasource.WXArticleDataSourceFactory
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository
import java.util.concurrent.Executors

class WXAccountSubFragmentViewModel(private val cid: Int) : ViewModel() {
    private var mArticles: LiveData<PagedList<Article>>

    init {
        val factory = WXArticleDataSourceFactory(cid)
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

    }
    fun observeArticles(): LiveData<PagedList<Article>> {
        return mArticles
    }
}