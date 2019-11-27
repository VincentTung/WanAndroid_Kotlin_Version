package com.example.myapplication.repository;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.*
import com.example.myapplication.net.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectRepository {
    fun getKnowledge(): LiveData<ResultData<List<Tree>>> {
        val data: MutableLiveData<ResultData<List<Tree>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getProjectTree_Calls().enqueue(object :
            Callback<ResultData<List<Tree>>> {
            override fun onFailure(call: Call<ResultData<List<Tree>>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ResultData<List<Tree>>>,
                response: Response<ResultData<List<Tree>>>
            ) {
                data.value = response.body()
            }
        })
        return data
    }

    fun getProjectSubList(cid: Int, page: Int): LiveData<ResultData<PageData<List<Project>>>> {

        val data: MutableLiveData<ResultData<PageData<List<Project>>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getProjectSubList_Calls(page, cid)
            .enqueue(object : Callback<ResultData<PageData<List<Project>>>> {
                override fun onFailure(
                    call: Call<ResultData<PageData<List<Project>>>>,
                    t: Throwable
                ) {
                }
                override fun onResponse(
                    call: Call<ResultData<PageData<List<Project>>>>,
                    response: Response<ResultData<PageData<List<Project>>>>
                ) {
                    data.value = response.body()
                }

            })
        return data
    }
}
