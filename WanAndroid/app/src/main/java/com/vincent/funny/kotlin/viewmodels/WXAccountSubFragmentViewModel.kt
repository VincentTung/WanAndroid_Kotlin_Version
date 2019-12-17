package com.vincent.funny.kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.vincent.funny.kotlin.cfg.EXECUTOR_SIZE
import com.vincent.funny.kotlin.cfg.PAGE_ITEMS_SIZE
import com.vincent.funny.kotlin.datasource.WXArticleDataSourceFactory
import com.vincent.funny.kotlin.entity.Article
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