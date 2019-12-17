package com.vincent.funny.kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.vincent.funny.kotlin.entity.Tree
import com.vincent.funny.kotlin.entity.ResultData
import com.vincent.funny.kotlin.repository.KnowledgeRepository

class KnowledgeViewModel internal constructor(private val knowledgeRepository: KnowledgeRepository):ViewModel() {

    private var knowledgeTrees: MediatorLiveData<List<Tree>> = MediatorLiveData()

    fun observeKnowledgeTrees(): LiveData<List<Tree>> {
        return knowledgeTrees
    }

    fun getKnowledgeTrees(){
        val data :LiveData<ResultData<List<Tree>>> = knowledgeRepository.getKnowledge()
        knowledgeTrees.addSource(data) {
            knowledgeTrees.value =  it.data
            knowledgeTrees.removeSource(data)
        }
    }
}