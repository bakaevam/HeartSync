package com.heartsync.features.authphone.enteremail.domain

interface EnterEmailRepository {

    suspend fun sendEmailLink(email: String)

    suspend fun signUpByEmail(
        emailLink: String,
        email: String,
    )
}