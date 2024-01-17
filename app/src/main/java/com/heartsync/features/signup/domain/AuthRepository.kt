package com.heartsync.features.signup.domain

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun singUpByEmailPassword(
        email: String,
        password: String,
    )

    suspend fun signUpByPhone(code: String)

    suspend fun signUpByToken(token: String)

    suspend fun signInByEmailPassword(
        email: String,
        password: String,
    )

    suspend fun signInByPhone(code: String)

    suspend fun signInByToken(token: String)

    fun getSignUpRequest(): BeginSignInRequest

    fun observeNewUser(): Flow<String?>

    fun resetNewUser()

    fun isAuthentication(): Flow<Boolean>
}