package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.PageData
import com.example.myapplication.entity.Project
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.entity.ResultData
import com.example.myapplication.util.BaseObserver
import com.example.myapplication.util.ComposeUtil
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.fragment_knowledge_sub.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository {

    fun getArticles(page: Int): LiveData<ResultData<PageData<List<Article>>>> {

        val data: MutableLiveData<ResultData<PageData<List<Article>>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getArticle_Calls(page)
            .enqueue(object : Callback<ResultData<PageData<List<Article>>>> {
                override fun onFailure(
                    call: Call<ResultData<PageData<List<Article>>>>,
                    t: Throwable
                ) {

                }

                override fun onResponse(
                    call: Call<ResultData<PageData<List<Article>>>>,
                    response: Response<ResultData<PageData<List<Article>>>>
                ) {
                    data.value = response.body()
                }
            })
        return data
    }

    fun getArticlesUnderTree(cid: Int, page: Int): LiveData<ResultData<PageData<List<Article>>>> {

        val data: MutableLiveData<ResultData<PageData<List<Article>>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getArticleUnderTree_Calls(page, cid)
            .enqueue(object : Callback<ResultData<PageData<List<Article>>>> {
                override fun onFailure(
                    call: Call<ResultData<PageData<List<Article>>>>,
                    t: Throwable
                ) {
                }
                override fun onResponse(
                    call: Call<ResultData<PageData<List<Article>>>>,
                    response: Response<ResultData<PageData<List<Article>>>>
                ) {
                    data.value = response.body()
                }

            })
        return data
    }


}