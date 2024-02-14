package com.heartsync.di

import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import org.koin.dsl.module

val useCaseModule = module {
    factory<SignOutUseCase> {
        SignOutUseCase(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
}