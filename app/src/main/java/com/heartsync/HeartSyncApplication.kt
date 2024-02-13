package com.heartsync

import android.app.ActivityManager
import android.app.Application
import android.webkit.WebView
import com.heartsync.core.providers.repositoryModule
import com.heartsync.di.navigationModule
import com.heartsync.di.providerModule
import com.heartsync.di.useCaseModule
import com.heartsync.di.viewModelModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class HeartSyncApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (isMainProcess()) {
            startKoin {
                androidContext(this@HeartSyncApplication)
                modules(
                    navigationModule,
                    viewModelModule,
                    providerModule,
                    repositoryModule,
                    useCaseModule,
                )
            }
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG)
            // TODO add Logger
//            Logger.initialize(
//                localEnabled = BuildConfig.LOGGING_ENABLED,
//                remoteEnabled = !BuildConfig.DEBUG,
//                application = this,
//            )
        }
    }

    private fun isMainProcess(): Boolean {
        val pid = android.os.Process.myPid()
        val packageName = packageName
        return getSystemService(ActivityManager::class.java)
            .runningAppProcesses
            ?.any { it.pid == pid && it.processName == packageName }
            ?: false
    }
}