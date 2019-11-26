package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.BannerRepository
import com.example.myapplication.repository.KnowledgeRepository

class MainPageViewModelFactory  constructor(val bannerRepository: BannerRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MainPageViewModel(bannerRepository) as T
    }
}