package com.vincent.funny.kotlin.entity


data class Chapter (
    val courseId: Int = 0,
    val id: Int = 0,
    val name: String ?,
    val order: Long? = null,
    val parentChapterId: Int = 0,
    val userControlSetTop: Boolean? = null,
    val visible: Int = 0
)