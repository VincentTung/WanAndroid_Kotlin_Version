package com.example.myapplication.net

import com.example.myapplication.app.WanApplication
import com.example.myapplication.cfg.WanUrl
import com.example.myapplication.net.interceptor.NetWorkStateInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiHelper private constructor() {

    private lateinit var mOkHttpClient: OkHttpClient
    private lateinit var mRetrofit: Retrofit
    private lateinit var mApiService: ApiService

    init {
        initRetrofit()
    }

    companion object {
        private const val CACHE_SIZE: Long = 10 * 1024 * 1024

        /**
         * 单例
         */
        @Volatile
        private var mInstance: ApiHelper? = null

        /**
         * DCL
         */
        fun getInstance(): ApiHelper {
            return mInstance ?: synchronized(this) {
                mInstance ?: ApiHelper().also { mInstance = it }
            }
        }
    }

    private fun initRetrofit() {

        val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        okHttpBuilder.addInterceptor(NetWorkStateInterceptor())

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(loggingInterceptor)
        okHttpBuilder.cache(Cache(WanApplication.mInstance.getExternalFilesDir(null), CACHE_SIZE))

        mOkHttpClient = okHttpBuilder.build()

        val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        mRetrofit = retrofitBuilder.baseUrl(WanUrl.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build()

        mApiService = mRetrofit.create(ApiService::class.java)
    }

    fun getApiService(): ApiService {
        return mApiService
    }

}