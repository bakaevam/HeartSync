package com.heartsync.core.providers

import com.heartsync.core.network.db.FirebaseDatabase
import com.heartsync.core.network.db.FirebaseDatabaseImpl
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.authphone.enteremail.data.EnterEmailRepositoryImpl
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.authphone.smscode.data.SmsCodeRepositoryImpl
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.welcome.data.repositories.WelcomeRepositoryImpl
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<FirebaseDatabase> {
        FirebaseDatabaseImpl()
    }
    single<WelcomeRepository> {
        WelcomeRepositoryImpl(
            database = get<FirebaseDatabase>(),
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
}