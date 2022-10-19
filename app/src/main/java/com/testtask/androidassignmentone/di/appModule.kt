package com.testtask.androidassignmentone.di

import com.testtask.androidassignmentone.data.network.ApiManager
import com.testtask.androidassignmentone.data.repository.RepositorySlideShow
import com.testtask.androidassignmentone.view.fragments.slideShowFragment.SlideShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Retrofit
    single { ApiManager() }

    // Repository
    single { RepositorySlideShow(get()) }

    // ViewModel
    viewModel{ SlideShowViewModel(get())}
}