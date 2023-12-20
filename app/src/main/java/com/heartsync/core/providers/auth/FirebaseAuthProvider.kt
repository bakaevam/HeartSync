package com.heartsync.core.providers.auth

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseAuthProvider {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun getSignUpRequest(): BeginSignInRequest =
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(getSignUpRequestOptions())
            .build()

    fun getSignInRequest(): BeginSignInRequest =
        BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(getSignInRequestOptions())
            .setAutoSelectEnabled(true)
            .build()

    suspend fun signUpUser(idToken: String): AuthResult {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        return firebaseAuth.signInWithCredential(firebaseCredential)
            .await()
    }

    fun isAuthentication(): Flow<Boolean> = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser != null)
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        awaitClose {
            firebaseAuth.removeAuthStateListener(authStateListener)
        }
    }

    private fun getSignInRequestOptions(): BeginSignInRequest.GoogleIdTokenRequestOptions =
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setServerClientId(CLIENT_ID)
            .setFilterByAuthorizedAccounts(true)
            .build()

    private fun getSignUpRequestOptions(): BeginSignInRequest.GoogleIdTokenRequestOptions =
        BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
            .setSupported(true)
            .setServerClientId(CLIENT_ID)
            .setFilterByAuthorizedAccounts(false)
            .build()

    private companion object {

        private const val CLIENT_ID =
            "392883523693-o7bsl5flau9kv92pn0tn3kcopsbsqp2m.apps.googleusercontent.com"
    }
}