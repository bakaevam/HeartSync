package com.heartsync.features.signup.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.main.data.mappers.UserMapper
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.FirebaseDatabase
import com.heartsync.features.profiledetail.domain.UserInfo
import com.heartsync.features.signup.domain.AuthRepository
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val firebaseDatabase: FirebaseDatabase,
) : AuthRepository {

    private val newUserFlow = MutableStateFlow<String?>(null)

    override suspend fun singUpByEmailPassword(email: String, password: String) {
        val authResult = firebaseAuthProvider.signUpWithPassword(email, password)
        val userUid = authResult.user?.uid
        addUserToDb(userUid)
    }

    override suspend fun signUpByPhone(code: String) {
        val authResult = firebaseAuthProvider.signUpByPhone(code)
        val userUid = authResult.user?.uid
        addUserToDb(userUid)
    }

    override suspend fun signUpByToken(token: String) {
        val authResult = firebaseAuthProvider.signUpUser(token)
        val userUid = authResult.user?.uid
        addUserToDb(userUid)
    }

    override suspend fun signInByEmailPassword(email: String, password: String) {
        firebaseAuthProvider.signInWithPassword(email, password)
    }

    override suspend fun signInByPhone(code: String) {
        firebaseAuthProvider.signUpByPhone(code)
    }

    override suspend fun signInByToken(token: String) {
        firebaseAuthProvider.signUpUser(token)
    }

    override fun getSignUpRequest(): BeginSignInRequest =
        firebaseAuthProvider.getSignUpRequest()

    override fun observeNewUser(): Flow<String?> =
        newUserFlow

    override fun resetNewUser() {
        newUserFlow.value = null
    }

    override fun isAuthentication(): Flow<Boolean> =
        firebaseAuthProvider.isAuthentication()

    private suspend fun addUserToDb(userUid: String?) {
        withContext(NonCancellable) {
            if (userUid != null) {
                firebaseDatabase.createUserInfo(
                    userUid = userUid,
                    dbUserInfo = UserMapper.toDbUserInfo(createUser(userUid)),
                )
                newUserFlow.value = userUid
            }
        }
    }

    private fun createUser(userUid: String): UserInfo =
        UserInfo(
            id = userUid,
            name = EMPTY_STRING,
            lastname = EMPTY_STRING,
            birthday = null,
            gender = EMPTY_STRING,
        )
}