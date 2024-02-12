package com.heartsync.features.profiledetail.data.repository

import com.heartsync.core.network.db.FirebaseDatabase
import com.heartsync.core.network.db.mappers.UserMapper
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import java.time.LocalDate

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val firebaseAuthProvider: FirebaseAuthProvider,
) : UserRepository {

    override suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
    ) {
        val userUid = firebaseAuthProvider.getUserUid()
        if (userUid != null) {
            database.updateUserInfo(
                userUid = userUid,
                dbUserInfo = UserMapper.createDbUser(
                    name = name,
                    lastName = lastname,
                    birthday = birthday,
                )
            )
        }
    }
}