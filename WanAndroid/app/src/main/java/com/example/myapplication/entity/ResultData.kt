package com.example.myapplication.entity

data class ResultData<T>(

    val data: T? = null,
    val errorCode: Int = 0,
    val errorMsg: String? = ""
)