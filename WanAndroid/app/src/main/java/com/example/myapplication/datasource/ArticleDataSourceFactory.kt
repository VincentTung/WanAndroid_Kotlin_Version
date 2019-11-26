package com.example.myapplication.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository

class ArticleDataSourceFactory : androidx.paging.DataSource.Factory<Int, Article>() {
    private val mArticleDataSource: ArticleDataSource = ArticleDataSource(ArticleRepository())
    private val mLiveArticleDataSource: MutableLiveData<ArticleDataSource> = MutableLiveData()
    override fun create(): androidx.paging.DataSource<Int, Article> {
        mLiveArticleDataSource.postValue(mArticleDataSource)
        return mArticleDataSource
    }

    fun observeArticleDataSource(): LiveData<ArticleDataSource> {
        return mLiveArticleDataSource;
    }
}