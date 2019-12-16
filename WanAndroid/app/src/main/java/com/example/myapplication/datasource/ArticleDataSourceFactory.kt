package com.example.myapplication.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository

class ArticleDataSourceFactory : androidx.paging.DataSource.Factory<Int, Article>() {
    private lateinit var mArticleDataSource: ArticleDataSource
    private val mLiveArticleDataSource: MutableLiveData<ArticleDataSource> = MutableLiveData()
    override fun create(): androidx.paging.DataSource<Int, Article> {
        mArticleDataSource = ArticleDataSource(ArticleRepository())
        mLiveArticleDataSource.postValue(mArticleDataSource)
        return mArticleDataSource
    }

    fun observeArticleDataSource(): LiveData<ArticleDataSource> {
        return mLiveArticleDataSource
    }

    fun getDataSource(): ArticleDataSource {
        return mArticleDataSource
    }
}