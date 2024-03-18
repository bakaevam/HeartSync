package com.heartsync.features.main.data.providers.auth

import android.app.Activity
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.actionCodeSettings
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.PACKAGE_NAME
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class FirebaseAuthProvider {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val phoneAuthCallback =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                } else if (e is FirebaseAuthMissingActivityForRecaptchaException) {
                    // reCAPTCHA verification attempted with null Activity
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token
            }
        }

    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    init {
        firebaseAuth.setLanguageCode(LANGUAGE_RUS)
    }

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
        return signInWithCredential(firebaseCredential).await()
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

    suspend fun signUpByPhone(code: String): AuthResult {
        val credential = PhoneAuthProvider.getCredential(storedVerificationId ?: EMPTY_STRING, code)
        return signInWithCredential(credential).await()
    }

    fun sendVerificationCode(
        phoneNumber: String,
        activity: Activity,
    ) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(activity) // Activity (for callback binding)
            .setCallbacks(phoneAuthCallback) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    suspend fun sendEmailLink(email: String) {
        val settings = actionCodeSettings {
            url = URL_FINISH_SIGN_UP + email
            handleCodeInApp = true
            dynamicLinkDomain = DOMAIN_NAME
            setAndroidPackageName(
                PACKAGE_NAME,
                true, // installIfNotAvailable
                "1.0", // minimumVersion
            )
        }
        firebaseAuth.sendSignInLinkToEmail(email, settings).await()
    }

    suspend fun signUpByEmail(emailLink: String, email: String) {
        if (firebaseAuth.isSignInWithEmailLink(emailLink)) {
            firebaseAuth.signInWithEmailLink(email, emailLink).await()
        }
    }

    suspend fun signUpWithPassword(
        email: String,
        password: String,
    ): AuthResult =
        firebaseAuth.createUserWithEmailAndPassword(email, password).await()

    suspend fun signInWithPassword(
        email: String,
        password: String,
    ): AuthResult =
        firebaseAuth.signInWithEmailAndPassword(email, password).await()

    fun getCurrentUser(): FirebaseUser? =
        firebaseAuth.currentUser

    fun signOut() {
        firebaseAuth.signOut()
    }

    suspend fun createJwtToken(): String? =
        firebaseAuth.currentUser?.getIdToken(false)?.await()?.token

    private fun signInWithCredential(credential: AuthCredential) =
        firebaseAuth.signInWithCredential(credential)

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

    companion object {

        const val KEY_EMAIL = "email"
        const val URL_FINISH_SIGN_UP = "https://heartsync.page.link/finishSignUp/?$KEY_EMAIL="
        const val CONTINUE_URL = "continueUrl"

        private const val CLIENT_ID =
            "392883523693-o7bsl5flau9kv92pn0tn3kcopsbsqp2m.apps.googleusercontent.com"
        private const val LANGUAGE_RUS = "ru"
        private const val TAG = "Firebase Auth Provider"
        private const val DOMAIN_NAME = "heartsync.page.link"
    }
}