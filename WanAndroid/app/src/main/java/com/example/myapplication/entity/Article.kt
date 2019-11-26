package com.example.myapplication.entity

import android.os.Parcel
import android.os.Parcelable


data class Article(
    val title: String? = null,
    val type: Int = 0,
    val userId: Int = 0,
    val visible: Int = 0,
    val zan: Int = 0,
    val apkLink: String? = null,
    val author: String? = null,
    val chapterId: Int = 0,
    val chapterName: String? = null,
    val collect: String? = null,
    val courseId: Int = 0,
    val desc: String? = null,
    val envelopePic: String? = null,
    val fresh: Boolean? = null,
    val id: Int = 0,
    val link: String? = null,
    val niceDate: String? = null,
    val origin: String? = null,
    val prefix: String? = null,
    val projectLink: String? = null,
    val publishTime: Long? = null,
    val superChapterId: Int = 0,
    val superChapterName: String? = null
) {

}