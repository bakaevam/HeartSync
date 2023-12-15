package com.heartsync.di

import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            appNavigator = get<AppNavigator>(),
        )
    }
}