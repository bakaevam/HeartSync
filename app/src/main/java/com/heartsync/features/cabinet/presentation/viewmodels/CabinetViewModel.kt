package com.heartsync.features.cabinet.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route
import com.heartsync.features.cabinet.domain.usecase.SignOutUseCase
import com.heartsync.features.cabinet.presentation.model.ProfileDataMapper
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import kotlinx.coroutines.launch

class CabinetViewModel(
    private val signOutUseCase: SignOutUseCase,
    private val userRepository: UserRepository,
    private val appNavigator: AppNavigator,
) : MviViewModel<CabinetState, CabinetEffect, CabinetAction>(CabinetState()) {

    init {
        loadProfileData()
    }

    override fun onAction(action: CabinetAction) = when (action) {
        is CabinetAction.OnSignOutClick -> signOutUseCase()
        is CabinetAction.OnOpenCameraClick -> onOpenCameraClick()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                val profileData = userRepository.getProfileData()
                setState { copy(uiProfileData = profileData?.let(ProfileDataMapper::toUiProfileData)) }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to load profile data", e)
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun onOpenCameraClick() {
        appNavigator.tryNavigateTo(
            route = Route.CAMERA.key,
        )
    }

    private companion object {
        private const val TAG = "Cabinet"
    }
}