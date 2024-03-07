package com.heartsync.features.profiledetail.data.repository

import com.heartsync.core.providers.ChatProvider
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

    override fun getClientState(): ClientState? {
        return chatProvider.getClientState()
    }

    override suspend fun initChats() {
        val userUid = firebaseAuthProvider.getUserUid()
        val token = firebaseAuthProvider.createJwtToken()
        if (userUid != null && token != null) {
            chatProvider.initialize(
                userUid = userUid,
                nickname = "Nickname",
                token = token,
            )
        }
    }
}