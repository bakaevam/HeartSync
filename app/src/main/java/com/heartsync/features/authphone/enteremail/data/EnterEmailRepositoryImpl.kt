package com.heartsync.features.authphone.enteremail.data

import com.google.firebase.auth.AuthResult
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider

class EnterEmailRepositoryImpl(
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : EnterEmailRepository {

    override suspend fun signUpWithPassword(email: String, password: String) =
        firebaseAuthProvider.signUpWithPassword(email, password)

    override suspend fun signInWithPassword(email: String, password: String): AuthResult =
        firebaseAuthProvider.signInWithPassword(email, password)

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