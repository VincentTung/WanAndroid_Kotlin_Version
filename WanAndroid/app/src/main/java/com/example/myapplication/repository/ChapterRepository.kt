package com.example.myapplication.repository;

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.entity.Chapter
import com.example.myapplication.entity.ResultData
import com.example.myapplication.net.ApiHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

 class ChapterRepository {

    fun getChapter(): LiveData<ResultData<List<Chapter>>> {
        val data: MutableLiveData<ResultData<List<Chapter>>> = MutableLiveData()
        ApiHelper.getInstance().getApiService().getChapterData_Calls()
            .enqueue(object : Callback<ResultData<List<Chapter>>> {
                override fun onResponse(
                    call: Call<ResultData<List<Chapter>>>,
                    response: Response<ResultData<List<Chapter>>>
                ) {
                    data.value = response.body()
                }

                override fun onFailure(call: Call<ResultData<List<Chapter>>>, t: Throwable) {

                }

            })

        return data
    }
}
