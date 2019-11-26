package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Tree
import com.example.myapplication.net.ApiHelper
import com.example.myapplication.entity.ResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class KnowledgeRepository {

    fun getKnowledge(): LiveData<ResultData<List<Tree>>> {
        val data: MutableLiveData<ResultData<List<Tree>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getKnowledgeTree_Calls().enqueue(object :
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
}