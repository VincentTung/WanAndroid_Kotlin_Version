package com.vincent.funny.kotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vincent.funny.kotlin.entity.Tree
import com.vincent.funny.kotlin.net.ApiHelper
import com.vincent.funny.kotlin.entity.ResultData
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