package com.heartsync.di

import com.heartsync.features.camera.data.providers.CameraProvider
import com.heartsync.features.main.data.providers.ContextProvider
import com.heartsync.features.main.data.providers.FileProvider
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.FirebaseDatabase
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providerModule = module {
    single<ContextProvider> {
        ContextProvider(
            context = androidContext(),
        )
    }
    single<TextProvider> {
        TextProvider(
            resources = get<ContextProvider>().getResources(),
        )
    }
    single<FirebaseAuthProvider> {
        FirebaseAuthProvider()
    }
    single<FirebaseDatabase> {
        FirebaseDatabase()
    }
    single<CameraProvider> {
        CameraProvider(
            context = androidApplication(),
        )
    }
    single<FileProvider> {
        FileProvider(
            dateTimeRepository = get<DateTimeRepository>(),
        )
    }
}