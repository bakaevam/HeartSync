package com.heartsync.features.profiledetail.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileDetailViewModel(
    private val appNavigator: AppNavigator,
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
        is ProfileDetailAction.OnEditAvatarClick -> TODO()
        is ProfileDetailAction.OnBirthdayClick -> TODO()
    }

    private fun onNameChange(action: ProfileDetailAction.OnNameChange) {
        nameFlow.value = action.name
    }

    private fun onLastnameChange(action: ProfileDetailAction.OnLastnameChange) {
        lastnameFlow.value = action.lastname
    }

    private fun onSkipClick() {
        appNavigator.tryNavigateTo(Destination.DiscoveryScreen.fullRoute)
    }

    private fun onSaveClick() {
        viewModelScope.launch {
            try {

            } catch (e: Throwable) {
                Log.e(TAG, "Failed to save profile detail info", e)
                postEffect(ProfileDetailEffect.ShowError())
            }
        }
    }

    class Params(
        val name: String?,
        val lastName: String?,
    )

    private companion object {
        private const val TAG = "Profile Detail View Model"
    }
}