package com.example.myapplication.net

import com.example.myapplication.cfg.WanUrl
import com.example.myapplication.entity.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(WanUrl.URL_BANNER)
    fun getBanner(): Observable<ResultData<List<Banner>>>


    @GET("/article/list/{page}/json")
    fun getArticle(@Path("page") page: Int): Observable<ResultData<ArticleData<List<Article>>>>


    @GET(WanUrl.URL_TREE)
    fun getKnowledgeTree(): Observable<ResultData<List<KnowledgeTree>>>


    @GET("/article/list/{page}/json")
    fun getArticleUnderTree(@Path("page") page: Int, @Query("cid") cid: Int): Observable<ResultData<ArticleData<List<Article>>>>

    @GET(WanUrl.URL_CHAPTER)
    fun getChapterData(): Observable<ResultData<List<Chapter>>>


}