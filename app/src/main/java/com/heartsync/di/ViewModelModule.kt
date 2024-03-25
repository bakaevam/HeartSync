package com.heartsync.di

import androidx.lifecycle.SavedStateHandle
import com.heartsync.core.providers.ChatProvider
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneViewModel
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailViewModel
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.authphone.smscode.presentation.viewmodels.SmsCodeViewModel
import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetViewModel
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.camera.presentation.viewmodels.CameraViewModel
import com.heartsync.features.chat.presentation.viewmodels.ChatViewModel
import com.heartsync.features.discovery.domain.usecase.LoadUsersUseCase
import com.heartsync.features.discovery.presentation.viewmodels.DiscoveryViewModel
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.domain.repositories.ChatRepository
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import com.heartsync.features.main.domain.repositories.PermissionRepository
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import com.heartsync.features.matches.presentation.viewmodels.MatchesViewModel
import com.heartsync.features.messages.presentation.viewmodels.MessagesViewModel
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailViewModel
import com.heartsync.features.signup.domain.AuthRepository
import com.heartsync.features.signup.presentation.viewmodels.SignUpViewModel
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository
import com.heartsync.features.welcome.presentation.viewmodels.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        MainViewModel(
            appNavigator = get<AppNavigator>(),
            enterEmailRepository = get<EnterEmailRepository>(),
            authRepository = get<AuthRepository>(),
            userRepository = get<UserRepository>(),
        )
    }
    viewModel {
        WelcomeViewModel(
            appNavigator = get<AppNavigator>(),
            welcomeRepository = get<WelcomeRepository>(),
        )
    }
    viewModel { (arguments: SavedStateHandle) ->
        SignUpViewModel(
            appNavigator = get<AppNavigator>(),
            savedStateHandle = arguments,
            textProvider = get<TextProvider>(),
            authRepository = get<AuthRepository>(),
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
            authRepository = get<AuthRepository>(),
        )
    }
    viewModel { (arguments: SavedStateHandle) ->
        EnterEmailViewModel(
            appNavigator = get<AppNavigator>(),
            textProvider = get<TextProvider>(),
            savedStateHandle = arguments,
            authRepository = get<AuthRepository>(),
        )
    }
    viewModel {
        DiscoveryViewModel(
            appNavigator = get<AppNavigator>(),
            loadUsers = get<LoadUsersUseCase>(),
            chatRepository = get<ChatRepository>(),
        )
    }
    viewModel {
        ProfileDetailViewModel(
            appNavigator = get<AppNavigator>(),
            userRepository = get<UserRepository>(),
            cameraRepository = get<CameraRepository>(),
            textProvider = get<TextProvider>(),
        )
    }
    viewModel {
        MatchesViewModel()
    }
    viewModel {
        MessagesViewModel(
            appNavigator = get<AppNavigator>(),
            userRepository = get<UserRepository>(),
            textProvider = get<TextProvider>(),
            chatProvider = get<ChatProvider>(),
            dateTimeRepository = get<DateTimeRepository>(),
        )
    }
    viewModel {
        CabinetViewModel(
            signOutUseCase = get<SignOutUseCase>(),
            userRepository = get<UserRepository>(),
            appNavigator = get<AppNavigator>(),
            cameraRepository = get<CameraRepository>(),
        )
    }
    viewModel {
        CameraViewModel(
            appNavigator = get<AppNavigator>(),
            cameraRepository = get<CameraRepository>(),
            textProvider = get<TextProvider>(),
            permissionRepository = get<PermissionRepository>(),
        )
    }
    viewModel { (arguments: SavedStateHandle) ->
        ChatViewModel(
            appNavigator = get<AppNavigator>(),
            savedStateHandle = arguments,
        )
    }
}