package com.heartsync.features.authphone.enteremail.domain

import com.google.firebase.auth.AuthResult

interface EnterEmailRepository {

    suspend fun signUpWithPassword(
        email: String,
        password: String,
    ): AuthResult

    suspend fun sendEmailLink(email: String)

    suspend fun signUpByEmail(
        emailLink: String,
        email: String,
    )
}