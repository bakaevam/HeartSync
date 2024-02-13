package com.heartsync.di

import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory<SignOutUseCase> {
        SignOutUseCase(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
}