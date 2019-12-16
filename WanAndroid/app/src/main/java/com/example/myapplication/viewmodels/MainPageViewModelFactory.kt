package com.example.myapplication.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.repository.BannerRepository

class MainPageViewModelFactory  constructor(private val bannerRepository: BannerRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T{
        return MainPageViewModel(bannerRepository) as T
    }
}