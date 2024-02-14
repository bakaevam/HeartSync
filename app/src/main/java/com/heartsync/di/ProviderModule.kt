package com.heartsync.di

import com.heartsync.features.main.data.providers.ContextProvider
import com.heartsync.features.main.data.providers.TextProvider
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.FirebaseDatabase
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
}