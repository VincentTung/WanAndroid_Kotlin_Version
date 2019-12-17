package com.vincent.funny.kotlin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vincent.funny.kotlin.repository.KnowledgeRepository

class KnowledgeViewModelFactory  constructor(private val knowledgeRepository: KnowledgeRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return KnowledgeViewModel(knowledgeRepository) as T
    }
}