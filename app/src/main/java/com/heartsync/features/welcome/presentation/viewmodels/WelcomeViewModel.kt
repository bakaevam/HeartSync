package com.heartsync.features.welcome.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.features.welcome.domain.models.AuthScenario
import com.heartsync.features.welcome.domain.repositories.WelcomeRepository
import com.heartsync.features.welcome.presentation.models.WelcomePhotoMapper
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val appNavigator: AppNavigator,
    private val welcomeRepository: WelcomeRepository,
) : MviViewModel<WelcomeState, WelcomeEffect, WelcomeAction>(WelcomeState()) {

    init {
        viewModelScope.launch {
            try {
                setState { copy(loading = true) }
                val pages = welcomeRepository.getWelcomePages()
                setState { copy(gallery = WelcomePhotoMapper.toUiWelcomePages(pages)) }
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to load welcome pages", e)
            } finally {
                setState { copy(loading = false) }
            }
        }
    }

    override fun onAction(action: WelcomeAction) {
        when (action) {
            is WelcomeAction.OnCreateAccountClick -> {
                appNavigator.tryNavigateTo(
                    route = Destination.SignUpScreen.invoke(AuthScenario.SIGN_UP),
                )
            }

            is WelcomeAction.OnSignInCLick -> appNavigator.tryNavigateTo(
                route = Destination.SignUpScreen.invoke(AuthScenario.SIGN_IN),
            )
        }
    }

    private companion object {

        private const val TAG = "Welcome Screen"
    }
}