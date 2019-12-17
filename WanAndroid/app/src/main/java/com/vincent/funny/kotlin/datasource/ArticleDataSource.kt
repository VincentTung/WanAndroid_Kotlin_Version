package com.vincent.funny.kotlin.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.vincent.funny.kotlin.entity.Article
import com.vincent.funny.kotlin.repository.ArticleRepository
import com.vincent.funny.kotlin.util.LoadingState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class ArticleDataSource(private val articleRepository: ArticleRepository) :
    PageKeyedDataSource<Int, Article>() {

    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private val page: MediatorLiveData<Int> = MediatorLiveData()
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
                        null,
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
                it.data?.datas?.let { it1 ->
                    callback.onResult(it1, it!!.data!!.curPage)

                    page.value = it.data.curPage
                }
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

    fun observePage(): LiveData<Int> {
        return page
    }
}