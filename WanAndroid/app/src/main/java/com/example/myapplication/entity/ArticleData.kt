package com.example.myapplication.entity

class ArticleData<T> {

    var datas: T? = null
    var offset: Int = 0
    var over = false
    var pageCount: Int = 0
    var size: Int = 0
    var total: Int = 0
    var curPage:Int = 0
}