package com.heartsync.core.providers

import com.heartsync.core.network.db.FirebaseDatabase
import com.heartsync.core.network.store.FirebaseStore
import com.heartsync.core.network.store.FirebaseStoreImpl
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.authphone.enteremail.data.EnterEmailRepositoryImpl
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.authphone.smscode.data.SmsCodeRepositoryImpl
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.profiledetail.data.repository.UserRepositoryImpl
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import com.heartsync.features.signup.data.AuthRepositoryImpl
import com.heartsync.features.signup.domain.AuthRepository
import com.heartsync.features.welcome.data.repositories.WelcomeRepositoryImpl
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<FirebaseStore> {
        FirebaseStoreImpl()
    }
    single<WelcomeRepository> {
        WelcomeRepositoryImpl(
            database = get<FirebaseStore>(),
            textProvider = get<TextProvider>(),
        )
    }
    single<SmsCodeRepository> {
        SmsCodeRepositoryImpl(
            textProvider = get<TextProvider>(),
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
    single<EnterEmailRepository> {
        EnterEmailRepositoryImpl(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
        )
    }
    single<AuthRepository> {
        AuthRepositoryImpl(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
            firebaseDatabase = get<FirebaseDatabase>(),
        )
    }
    single<UserRepository> {
        UserRepositoryImpl(
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
            database = get<FirebaseDatabase>(),
        )
    }
}