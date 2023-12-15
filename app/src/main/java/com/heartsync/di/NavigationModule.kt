package com.heartsync.di

import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.AppNavigatorImpl
import org.koin.dsl.module

val navigationModule = module {
    single<AppNavigator> {
        AppNavigatorImpl()
    }
}