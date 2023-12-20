package com.heartsync.di

import com.heartsync.core.providers.ContextProvider
import com.heartsync.core.providers.TextProvider
import com.heartsync.core.providers.auth.FirebaseAuthProvider
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
}