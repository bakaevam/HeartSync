package com.heartsync.di

import androidx.lifecycle.SavedStateHandle
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneViewModel
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailViewModel
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.authphone.smscode.presentation.viewmodels.SmsCodeViewModel
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryViewModel
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
            enterEmailRepository = get<EnterEmailRepository>(),
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
    viewModel { (arguments: SavedStateHandle) ->
        SmsCodeViewModel(
            smsCodeRepository = get<SmsCodeRepository>(),
            savedStateHandle = arguments,
        )
    }
    viewModel {
        EnterEmailViewModel(
            appNavigator = get<AppNavigator>(),
            enterEmailRepository = get<EnterEmailRepository>(),
        )
    }
    viewModel {
        DiscoveryViewModel()
    }
}