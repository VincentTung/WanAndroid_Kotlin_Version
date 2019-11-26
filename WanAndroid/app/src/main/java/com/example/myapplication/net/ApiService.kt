package com.example.myapplication.net

import com.example.myapplication.cfg.WanUrl
import com.example.myapplication.entity.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(WanUrl.URL_BANNER)
    fun getBanner(): Observable<ResultData<List<Banner>>>


    @GET("/article/list/{page}/json")
    fun getArticle(@Path("page") page: Int): Observable<ResultData<PageData<List<Article>>>>


    @GET(WanUrl.URL_TREE)
    fun getKnowledgeTree(): Observable<ResultData<List<Tree>>>


    @GET("/article/list/{page}/json")
    fun getArticleUnderTree(@Path("page") page: Int, @Query("cid") cid: Int): Observable<ResultData<PageData<List<Article>>>>

    @GET(WanUrl.URL_CHAPTER)
    fun getChapterData(): Observable<ResultData<List<Chapter>>>

    @GET(WanUrl.URL_PROJECT)
    fun getProjectTree(): Observable<ResultData<List<Tree>>>

    @GET("/project/list/{page}/json")
    fun getProjectSubList(@Path("page") page: Int, @Query("cid") cid: Int): Observable<ResultData<PageData<List<Project>>>>


    @GET(WanUrl.URL_TREE)
    fun getKnowledgeTree_Calls(): retrofit2.Call<ResultData<List<Tree>>>

    @GET("/article/list/{page}/json")
    fun getArticle_Calls(@Path("page") page: Int): Call<ResultData<PageData<List<Article>>>>


    @GET(WanUrl.URL_BANNER)
    fun getBanner_Calls(): Call<ResultData<List<Banner>>>


}