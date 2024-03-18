package com.heartsync.features.profiledetail.data.repository

import com.heartsync.core.providers.ChatProvider
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.features.cabinet.domain.model.ProfileData
import com.heartsync.features.main.data.mappers.UserMapper
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.store.FirebaseDatabase
import com.heartsync.features.main.domain.store.StorageSource
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import io.getstream.chat.android.client.setup.state.ClientState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class UserRepositoryImpl(
    private val database: FirebaseDatabase,
    private val firebaseAuthProvider: FirebaseAuthProvider,
    private val storageSource: StorageSource,
    private val chatProvider: ChatProvider,
) : UserRepository {

    override suspend fun updateCurrentUser(
        name: String?,
        lastname: String?,
        birthday: LocalDate?,
        gender: String?,
    ) {
        val userUid = firebaseAuthProvider.getCurrentUser()?.uid
        if (userUid != null) {
            database.updateUserInfo(
                userUid = userUid,
                updates = buildMap {
                    name?.let { put(KEY_NAME, name) }
                    lastname?.let { put(KEY_LASTNAME, lastname) }
                    birthday?.let { put(KEY_BIRTHDAY, DateMapper.formatDayMonthYear(birthday)) }
                    gender?.let { put(KEY_GENDER, gender) }
                },
            )
        }
    }

    override suspend fun getProfileData(): ProfileData? =
        withContext(Dispatchers.Default) {
            val userUid = firebaseAuthProvider.getCurrentUser()?.uid
            val profileData = userUid?.let {
                database
                    .getUserInfo(userUid)
                    ?.let(UserMapper::toProfileData)
            }
            profileData?.copy(
                avatar = storageSource.getAvatar(userUid),
            )
        }

    override suspend fun getProfile(): ProfileData? =
        withContext(Dispatchers.Default) {
            val userUid = firebaseAuthProvider.getCurrentUser()?.uid
            userUid?.let {
                database
                    .getUserInfo(userUid)
                    ?.let(UserMapper::toProfileData)
            }
        }

    override fun getClientState(): ClientState? {
        return chatProvider.getClientState()
    }

    override suspend fun initChats() {
        val userUid = firebaseAuthProvider.getCurrentUser()?.uid
        val profileData = userUid?.let {
            database
                .getUserInfo(userUid)
                ?.let(UserMapper::toProfileData)
        }
        if (userUid != null && profileData != null) {
            chatProvider.initialize(
                userUid = userUid,
                nickname = profileData.name,
            )
        }
    }

    override suspend fun getAllUsers(): List<ProfileData> =
        withContext(Dispatchers.Default) {
            val userUid = firebaseAuthProvider.getCurrentUser()?.uid
            val profileData = userUid?.let {
                database
                    .getUserInfo(userUid)
                    ?.let(UserMapper::toProfileData)
            }
            database
                .getAllUsers()
                .asSequence()
                .map(UserMapper::toProfileData)
                .filter { it.id != profileData?.id }
                .toList()
        }

    override suspend fun getUserUid(): String? {
        return firebaseAuthProvider.getCurrentUser()?.uid
    }

    private companion object {
        private const val KEY_NAME = "name"
        private const val KEY_LASTNAME = "lastname"
        private const val KEY_BIRTHDAY = "birthday"
        private const val KEY_GENDER = "gender"
    }
}