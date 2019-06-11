package com.example.myapplication.net

import com.example.myapplication.cfg.WanUrl
import com.example.myapplication.entity.Article
import com.example.myapplication.entity.ArticleData
import com.example.myapplication.entity.Banner
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(WanUrl.URL_BANNER)
    fun getBanner(): Observable<ResultData<List<Banner>>>


    @GET("/article/list/{page}/json")
    fun getArticle(@Path("page") page: Int): Observable<ResultData<ArticleData<List<Article>>>>
}