package com.heartsync.features.profiledetail.data.repository

import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.main.data.mappers.UserMapper
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.FirebaseDatabase
import com.heartsync.features.main.domain.store.StorageSource
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val storageSource: StorageSource,
) : UserRepository {

    override suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
        gender: String,
    ) {
        val userUid = firebaseAuthProvider.getUserUid()
        if (userUid != null) {
            database.updateUserInfo(
                userUid = userUid,
                dbUserInfo = UserMapper.createDbUser(
                    name = name,
                    lastName = lastname,
                    birthday = birthday,
                    gender = gender,
                ),
            )
        }
    }

    override suspend fun getProfileData(): ProfileData? =
        withContext(Dispatchers.Default) {
            val userUid = firebaseAuthProvider.getUserUid()
            val profileData = userUid?.let {
                database
                    .getUserInfo(userUid)
                    ?.let(UserMapper::toProfileData)
            }
            profileData?.copy(
                avatar = storageSource.getAvatar(userUid),
            )
        }
}