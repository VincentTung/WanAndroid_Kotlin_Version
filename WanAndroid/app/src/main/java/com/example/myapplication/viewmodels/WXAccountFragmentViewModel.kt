package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.entity.Chapter
import com.example.myapplication.repository.ChapterRepository

class WXAccountFragmentViewModel : ViewModel() {
    private val mChapters: MediatorLiveData<List<Chapter>> = MediatorLiveData()
    private val mRepository = ChapterRepository()


    fun getChapters() {
        val source = mRepository.getChapter()
        mChapters.addSource(source, Observer {
            mChapters.value = it.data
            mChapters.removeSource(source)
        })
    }


    fun observeChapters(): LiveData<List<Chapter>> {
        return mChapters
    }
}