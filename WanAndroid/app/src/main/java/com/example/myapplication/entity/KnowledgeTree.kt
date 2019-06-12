package com.example.myapplication.entity


class KnowledgeTree {

    var children: List<KnowledgeTree>? = null
    var courseId: Int = 0
    var id: Int = 0
    var name: String = ""
    var order: Int = 0
    var parentChapterId: Int = 0
    var userControlSetTop: Boolean? = null
    var visible: Int = 0
}