package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.myapplication.datasource.ArticleDataSourceFactory
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.Banner
import com.example.myapplication.repository.ArticleRepository
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.util.LoadingState
import java.util.concurrent.Executors


class MainPageViewModel internal constructor(bannerRepository: BannerRepository) :
    ViewModel() {


    private val mArticles: MediatorLiveData<ArrayList<Article>> = MediatorLiveData()
    private var mLoadingState: MediatorLiveData<LoadingState> = MediatorLiveData()
    private val mBanners: MediatorLiveData<List<Banner>> = MediatorLiveData()

    private var mPage = 0

    private val mArticleRepository:ArticleRepository = ArticleRepository()
    p

    init {

        val source = bannerRepository.getBanners()
        mBanners.addSource(source) {
            mBanners.value = it.data
            mBanners.removeSource(source)
        }





    }


    fun getArticle( page:Int){

        val source = mArticleRepository.getArticles(page)
        mArticles.addSource(source, Observer {

             it.data?.datas?.let { it1 ->

                 var list = mArticles.value

                 list?.addAll(it1)
                 mArticles.value = list

             }

            mArticles.removeSource(source)
        })


    }
    fun refreshArticles(){
        mArticles.value = null
        mPage = 0
        getArticle(mPage)
    }

    fun observeArticles(): MediatorLiveData<ArrayList<Article>> {
        return mArticles
    }

    fun observeLoadingState(): LiveData<LoadingState> {
        return mLoadingState
    }

    fun observeBanners(): LiveData<List<Banner>> {
        return mBanners
    }

}