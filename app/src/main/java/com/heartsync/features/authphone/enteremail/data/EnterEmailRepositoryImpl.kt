package com.heartsync.features.authphone.enteremail.data

import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository

class EnterEmailRepositoryImpl(
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : EnterEmailRepository {

    override suspend fun signUpWithPassword(email: String, password: String) =
        firebaseAuthProvider.signUpWithPassword(email, password)

    override suspend fun sendEmailLink(email: String) {
        firebaseAuthProvider.sendEmailLink(email)
    }

    override suspend fun signUpByEmail(emailLink: String, email: String) {
        firebaseAuthProvider.signUpByEmail(
            emailLink = emailLink,
            email = email,
        )
    }
}