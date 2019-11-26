package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Banner
import com.example.myapplication.entity.ResultData
import com.example.myapplication.net.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BannerRepository {
    fun getBanners(): LiveData<ResultData<List<Banner>>> {

        val data: MutableLiveData<ResultData<List<Banner>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getBanner_Calls()
            .enqueue(object : Callback<ResultData<List<Banner>>> {
                override fun onResponse(
                    call: Call<ResultData<List<Banner>>>,
                    response: Response<ResultData<List<Banner>>>
                ) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<ResultData<List<Banner>>>, t: Throwable) {

                }
            })
        return data
    }
}