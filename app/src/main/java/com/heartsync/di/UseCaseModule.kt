package com.heartsync.di

import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase
import com.heartsync.features.discovery.domain.usecase.LoadUsersUseCase
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import org.koin.dsl.module

val useCaseModule = module {
    factory<SignOutUseCase> {
        SignOutUseCase(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
    factory<LoadUsersUseCase> {
        LoadUsersUseCase(
            userRepository = get<UserRepository>(),
        )
    }
}