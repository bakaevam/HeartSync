package com.heartsync.di

import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneViewModel
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import com.heartsync.features.signup.presentation.viewmodels.SignUpViewModel
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository
import com.heartsync.features.welcome.presentation.viewmodels.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            appNavigator = get<AppNavigator>(),
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
    viewModel {
        WelcomeViewModel(
            appNavigator = get<AppNavigator>(),
            welcomeRepository = get<WelcomeRepository>(),
        )
    }
    viewModel {
        SignUpViewModel(
            appNavigator = get<AppNavigator>(),
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
    viewModel {
        EnterPhoneViewModel(
            appNavigator = get<AppNavigator>(),
        )
    }
}