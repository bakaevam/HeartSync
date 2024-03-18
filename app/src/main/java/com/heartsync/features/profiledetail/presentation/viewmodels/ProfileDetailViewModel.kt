package com.heartsync.features.profiledetail.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.R
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.EMPTY_STRING
import com.heartsync.core.tools.INT_ONE
import com.heartsync.core.tools.format.DateFormatter
import com.heartsync.core.tools.format.DateMapper
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.ui.model.UiChooserItem
import com.heartsync.features.camera.domain.repositories.CameraRepository
import com.heartsync.features.main.data.providers.TextProvider
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
    private val textProvider: TextProvider,
) : MviViewModel<ProfileDetailState, ProfileDetailEffect, ProfileDetailAction>(
    ProfileDetailState()
) {

    private val nameFlow = MutableStateFlow(EMPTY_STRING)
    private val lastnameFlow = MutableStateFlow(EMPTY_STRING)
    private val birthdayFlow = MutableStateFlow<LocalDate?>(null)
    private var selectedGender: String? = null

    init {
        setGenders()
        nameFlow
            .onEach { name ->
                setState { copy(name = name) }
                validate()
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
        is ProfileDetailAction.OnEditAvatarClick -> appNavigator.tryNavigateTo(Destination.CameraScreen.fullRoute)
        is ProfileDetailAction.OnBirthdayClick -> onBirthdayClick()
        is ProfileDetailAction.OnBirthdayConfirm -> onBirthdayConfirm(action)
        is ProfileDetailAction.OnResume -> onResume(action)
        is ProfileDetailAction.OnGenderClick -> onGenderClick(action)
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

    private fun onSaveClick() {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                userRepository.updateCurrentUser(
                    name = nameFlow.value.takeIf { it.isNotEmpty() },
                    lastname = lastnameFlow.value.takeIf { it.isNotEmpty() },
                    birthday = birthdayFlow.value,
                    gender = selectedGender ?: throw Exception("Gender is null"),
                )
                appNavigator.tryNavigateBack()
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
        validate()
    }

    private fun onGenderClick(action: ProfileDetailAction.OnGenderClick) {
        selectedGender = action.id
        setState {
            copy(
                selectedGender = this@ProfileDetailViewModel.selectedGender,
            )
        }
        validate()
    }

    private fun setGenders() {
        setState {
            copy(
                genders = setOf(
                    UiChooserItem(
                        id = ID_WOMAN,
                        title = textProvider.getString(R.string.profile_detail_woman),
                    ),
                    UiChooserItem(
                        id = ID_MAN,
                        title = textProvider.getString(R.string.profile_detail_man),
                    ),
                ),
            )
        }
    }

    private fun validate() {
        val saveEnabled = nameFlow.value.isNotEmpty()
            && birthdayFlow.value != null
            && selectedGender != null
        setState { copy(saveEnabled = saveEnabled) }
    }

    private companion object {
        private const val TAG = "Profile Detail View Model"

        private const val AGE_OF_MAJORITY = 18L
        private const val ID_MAN = "man"
        private const val ID_WOMAN = "woman"
    }
}