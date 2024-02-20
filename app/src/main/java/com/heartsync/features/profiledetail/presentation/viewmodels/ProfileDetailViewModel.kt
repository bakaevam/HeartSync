package com.heartsync.features.profiledetail.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.format.DateFormatter
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.tools.navigation.Route
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileDetailViewModel(
    private val appNavigator: AppNavigator,
    private val userRepository: UserRepository,
    private val cameraRepository: CameraRepository,
) : MviViewModel<ProfileDetailState, ProfileDetailEffect, ProfileDetailAction>(
    ProfileDetailState()
) {

    private val nameFlow = MutableStateFlow(EMPTY_STRING)
    private val lastnameFlow = MutableStateFlow(EMPTY_STRING)
    private val birthdayFlow = MutableStateFlow<LocalDate?>(null)

    init {
        nameFlow
            .onEach { name ->
                setState { copy(name = name) }
            }
            .launchIn(viewModelScope)
        lastnameFlow
            .onEach { lastname ->
                setState { copy(lastName = lastname) }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(action: ProfileDetailAction) = when (action) {
        is ProfileDetailAction.OnLastnameChange -> onLastnameChange(action)
        is ProfileDetailAction.OnNameChange -> onNameChange(action)
        is ProfileDetailAction.OnSaveClick -> onSaveClick()
        is ProfileDetailAction.OnSkipClick -> onSkipClick()
        is ProfileDetailAction.OnEditAvatarClick -> appNavigator.tryNavigateTo(Destination.CameraScreen.fullRoute)
        is ProfileDetailAction.OnBirthdayClick -> onBirthdayClick()
        is ProfileDetailAction.OnBirthdayConfirm -> onBirthdayConfirm(action)
        is ProfileDetailAction.OnResume -> onResume(action)
    }

    private fun onResume(action: ProfileDetailAction.OnResume) {
        viewModelScope.launch {
            try {
                action.avatar?.let {
                    setState {
                        copy(
                            avatar = it,
                        )
                    }
                    cameraRepository.uploadAvatar(Uri.parse(it))
                }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to upload avatar")
            }
        }
    }

    private fun onNameChange(action: ProfileDetailAction.OnNameChange) {
        nameFlow.value = action.name
    }

    private fun onLastnameChange(action: ProfileDetailAction.OnLastnameChange) {
        lastnameFlow.value = action.lastname
    }

    private fun onSkipClick() {
        appNavigator.tryNavigateTo(
            route = Route.DISCOVERY.key,
            inclusive = true,
            isSingleTop = true,
            popBackStack = true,
        )
    }

    private fun onSaveClick() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                userRepository.updateCurrentUser(
                    name = nameFlow.value.takeIf { it.isNotEmpty() },
                    lastname = lastnameFlow.value.takeIf { it.isNotEmpty() },
                    birthday = birthdayFlow.value,
                )
                appNavigator.tryNavigateTo(Destination.DiscoveryScreen.fullRoute)
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to save profile detail info", e)
                postEffect(ProfileDetailEffect.ShowError())
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    private fun onBirthdayClick() {
        val date = birthdayFlow.value
        val today = LocalDate.now()
        postEffect(
            ProfileDetailEffect.ShowBirthdayPicker(
                year = date?.year ?: today.year,
                month = (date?.month?.value ?: today.month.value) - INT_ONE,
                day = date?.dayOfMonth ?: today.dayOfMonth,
                maxDay = DateMapper.toMillis(today.minusYears(AGE_OF_MAJORITY)),
            )
        )
    }

    private fun onBirthdayConfirm(action: ProfileDetailAction.OnBirthdayConfirm) {
        val birthday = LocalDate.of(action.year, action.month + INT_ONE, action.day)
        birthdayFlow.value = birthday
        setState { copy(birthday = DateFormatter.formatDateString(birthday)) }
    }

    private companion object {
        private const val TAG = "Profile Detail View Model"

        private const val AGE_OF_MAJORITY = 18L
    }
}