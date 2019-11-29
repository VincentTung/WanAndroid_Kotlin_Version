package com.example.myapplication.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository

class WXArticleDataSourceFactory (private  val cid:Int): androidx.paging.DataSource.Factory<Int, Article>() {
    private lateinit  var mArticleDataSource: WXArticleDataSource
    private val mLiveArticleDataSource: MutableLiveData<WXArticleDataSource> = MutableLiveData()
    override fun create(): androidx.paging.DataSource<Int, Article> {
        mArticleDataSource = WXArticleDataSource(ArticleRepository(),cid)
        mLiveArticleDataSource.postValue(mArticleDataSource)
        return mArticleDataSource
    }

    fun observeArticleDataSource(): LiveData<WXArticleDataSource> {
        return mLiveArticleDataSource;
    }

    fun getDataSource(): WXArticleDataSource {
        return mArticleDataSource
    }
}