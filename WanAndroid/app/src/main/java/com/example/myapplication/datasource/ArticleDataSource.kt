package com.example.myapplication.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.myapplication.entity.Article
import com.example.myapplication.repository.ArticleRepository
import com.example.myapplication.util.LoadingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class ArticleDataSource(private val articleRepository: ArticleRepository) :
    PageKeyedDataSource<Int, Article>() {

    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Article>
    ) {

        loadingState.postValue(LoadingState.LOADING_BEGIN)
        val source = articleRepository.getArticles(0)
        Observable.just(source).observeOn(AndroidSchedulers.mainThread()).subscribe {
            source.observeForever {
                it.data?.datas?.let { it1 ->
                    callback.onResult(
                        it1,
                        it!!.data!!.curPage - 1,
                        it!!.data!!.curPage
                    )
                    loadingState.postValue(LoadingState.LOADING_STOP)
                }
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        val source = articleRepository.getArticles(params.key)
        Observable.just(source).observeOn(AndroidSchedulers.mainThread()).subscribe {
            source.observeForever {
                it.data?.datas?.let { it1 -> callback.onResult(it1, it!!.data!!.curPage) }
                loadingState.postValue(LoadingState.LOADING_STOP)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Article>) {
        loadingState.postValue(LoadingState.LOADING_BEGIN)
    }


    fun observeLoadingState(): LiveData<LoadingState> {
        return loadingState
    }
}