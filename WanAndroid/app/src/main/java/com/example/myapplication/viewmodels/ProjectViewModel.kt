package com.example.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.myapplication.entity.PageData
import com.example.myapplication.entity.Project
import com.example.myapplication.entity.Tree
import com.example.myapplication.entity.ResultData
import com.example.myapplication.repository.KnowledgeRepository
import com.example.myapplication.repository.ProjectRepository

class ProjectViewModel : ViewModel() {

    private var mProjects: MediatorLiveData<List<Tree>> = MediatorLiveData()
    private var mProjectSubList: MediatorLiveData<List<Project>> = MediatorLiveData()
    private val mRepository = ProjectRepository()
    fun observeProjects(): LiveData<List<Tree>> {
        return mProjects
    }

    fun observeProjectSubList(): LiveData<List<Project>> {
        return mProjectSubList
    }

    fun getProjects() {
        val data: LiveData<ResultData<List<Tree>>> = mRepository.getKnowledge()
        mProjects.addSource(data) {
            mProjects.value = it.data
            mProjects.removeSource(data)
        }
    }

    fun getProjectSubList(page: Int, cid: Int) {
        val data: LiveData<ResultData<PageData<List<Project>>>> =
            mRepository.getProjectSubList(cid, page)
        mProjectSubList.addSource(data) {
            mProjectSubList.value = it.data?.datas
            mProjectSubList.removeSource(data)
        }
    }
}