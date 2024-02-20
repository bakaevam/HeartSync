package com.heartsync.features.main.data.providers

import com.heartsync.features.authphone.enteremail.data.EnterEmailRepositoryImpl
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.authphone.smscode.data.SmsCodeRepositoryImpl
import com.heartsync.features.authphone.smscode.domain.SmsCodeRepository
import com.heartsync.features.camera.data.providers.CameraProvider
import com.heartsync.features.camera.data.repositories.CameraRepositoryImpl
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.repositories.DateTimeRepositoryImpl
import com.heartsync.features.main.data.repositories.PermissionRepositoryImpl
import com.heartsync.features.main.data.store.FirebaseDatabase
import com.heartsync.features.main.data.store.FirebaseStoreImpl
import com.heartsync.features.main.data.store.StorageSourceImpl
import com.heartsync.features.main.domain.repositories.DateTimeRepository
import com.heartsync.features.main.domain.repositories.PermissionRepository
import com.heartsync.features.main.domain.store.FirebaseStore
import com.heartsync.features.main.domain.store.StorageSource
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
    single<StorageSource> {
        StorageSourceImpl()
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
            storageSource = get<StorageSource>(),
        )
    }
    single<DateTimeRepository> {
        DateTimeRepositoryImpl()
    }
    single<CameraRepository> {
        CameraRepositoryImpl(
            fileProvider = get<FileProvider>(),
            cameraProvider = get<CameraProvider>(),
            firebaseAuthProvider = get<FirebaseAuthProvider>(),
            storageSource = get<StorageSource>(),
        )
    }
    single<PermissionRepository> {
        PermissionRepositoryImpl(
            permissionsProvider = get<PermissionsProvider>(),
        )
    }
}