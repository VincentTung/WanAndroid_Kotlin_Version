package com.example.myapplication.entity

data class PageData<T> (

    val datas: T? = null,
    val offset: Int = 0,
    val over:Boolean = false,
    val pageCount: Int = 0,
    val size: Int = 0,
    val total: Int = 0,
    val curPage:Int = 0)