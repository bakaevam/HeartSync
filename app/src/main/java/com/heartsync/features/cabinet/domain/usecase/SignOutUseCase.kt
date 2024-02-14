package com.heartsync.features.cabinet.domain.usecase

import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider

class SignOutUseCase(
    private val firebaseAuthProvider: FirebaseAuthProvider,
) {

    operator fun invoke() {
        firebaseAuthProvider.signOut()
    }
}