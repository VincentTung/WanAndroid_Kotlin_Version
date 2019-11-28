package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.datasource.ArticleDataSourceFactory
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository
import java.util.concurrent.Executors

class WXAccountSubFragmentViewModel : ViewModel() {
    private lateinit var mArticles :LiveData<PagedList<Article>>
    init {
        val factory = ArticleDataSourceFactory()
        val executor = Executors.newFixedThreadPool(5)
        val pagedListConfig: PagedList.Config =
            PagedList.Config.Builder().setEnablePlaceholders(false).setInitialLoadSizeHint(10)
                .setPageSize(20).build()
        mArticles =
            LivePagedListBuilder(factory, pagedListConfig).setFetchExecutor(
                executor
            ).build()

    }


    fun observeArticles(): LiveData<PagedList<Article>> {
        return mArticles
    }
}