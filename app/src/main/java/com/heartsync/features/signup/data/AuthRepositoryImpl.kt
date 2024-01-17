package com.heartsync.features.signup.data

import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.heartsync.core.network.db.FirebaseDatabase
import com.heartsync.core.network.db.mappers.UserMapper
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.features.profiledetail.domain.UserInfo
import com.heartsync.features.signup.domain.AuthRepository

class AuthRepositoryImpl(
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val firebaseDatabase: FirebaseDatabase,
) : AuthRepository {

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

    private suspend fun addUserToDb(userUid: String?) {
        if (userUid != null) {
            firebaseDatabase.createUserInfo(
                userUid = userUid,
                dbUserInfo = UserMapper.toDbUserInfo(createUser(userUid)),
            )
        }
    }

    private fun createUser(userUid: String): UserInfo =
        UserInfo(
            id = userUid,
            name = EMPTY_STRING,
            lastname = EMPTY_STRING,
            birthday = null,
        )
}