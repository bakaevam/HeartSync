package com.heartsync.features.main.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.providers.auth.FirebaseAuthProvider.Companion.KEY_EMAIL
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.main.presentation.models.UiBottomItem
import com.heartsync.features.main.presentation.models.UiNavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    appNavigator: AppNavigator,
    firebaseAuthProvider: FirebaseAuthProvider,
    private val enterEmailRepository: EnterEmailRepository,
) : MviViewModel<MainState, MainEffect, MainAction>(
    MainState(
        bottomNavItems = listOf(
            UiNavItem(
                bottomItem = UiBottomItem.DISCOVERY,
                badgeText = null,
            ),
            UiNavItem(
                bottomItem = UiBottomItem.MATCHERS,
                badgeText = null,
            ),
            UiNavItem(
                bottomItem = UiBottomItem.MESSAGES,
                badgeText = null,
            ),
            UiNavItem(
                bottomItem = UiBottomItem.MENU,
                badgeText = null,
            ),
        ),
    )
) {

    val navigationChannel = appNavigator.navigationChannel
    private val currentNavItemFlow = MutableStateFlow<UiBottomItem?>(null)

    init {
        firebaseAuthProvider.isAuthentication()
            .onEach { authentication ->
                if (authentication) {
                    appNavigator.tryNavigateTo(
                        route = Route.DISCOVERY.key,
                        inclusive = true,
                        isSingleTop = true,
                        popBackStack = true,
                    )
                } else {
                    appNavigator.tryNavigateTo(
                        route = Route.WELCOME.key,
                        popUpToRoute = null,
                        inclusive = false,
                        isSingleTop = false,
                    )
                }
                Log.e("Main View Model", authentication.toString())
            }
            .launchIn(viewModelScope)

        currentNavItemFlow
            .onEach { tab ->
                setState { copy(currentNavItem = tab) }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(action: MainAction) = when (action) {
        is MainAction.OnNavItemClick -> onNavItemClick(action)
        is MainAction.OnNavigateDiscovery -> changeBottomNavBarVisibility(true)
        is MainAction.OnNavigateWelcome -> changeBottomNavBarVisibility(false)
        is MainAction.OnHandleDeeplink -> handleDeeplink(action)
    }

    private fun onNavItemClick(action: MainAction.OnNavItemClick) {
        currentNavItemFlow.value = action.bottomItem
    }

    private fun changeBottomNavBarVisibility(visible: Boolean) {
        setState { copy(bottomBarVisible = visible) }
        if (visible) {
            currentNavItemFlow.value = UiBottomItem.DISCOVERY
        } else {
            currentNavItemFlow.value = null
        }
    }

    private fun handleDeeplink(action: MainAction.OnHandleDeeplink) {
        val deepLink = action.deeplink
        Log.i(TAG, "Get deeplink = $deepLink")
        val params = deepLink?.queryParameterNames?.associateWith { deepLink.getQueryParameter(it) }
        params?.forEach { param ->
            if (param.key == FirebaseAuthProvider.CONTINUE_URL && param.value == FirebaseAuthProvider.URL_FINISH_SIGN_UP) {
                val continueUri = Uri.parse(param.value)
                val email = continueUri.getQueryParameter(KEY_EMAIL)
                email?.let {
                    signUpByEmail(
                        emailLink = deepLink.toString(),
                        email = email,
                    )
                }
            }
        }
    }

    private fun signUpByEmail(
        email: String,
        emailLink: String,
    ) {
        viewModelScope.launch {
            try {
                enterEmailRepository.signUpByEmail(
                    emailLink = emailLink,
                    email = email,
                )
            } catch (e: Throwable) {
                Log.e(TAG, "Failed to sign up by email", e)
                postEffect(MainEffect.ShowError())
            }
        }
    }

    private companion object {
        private const val TAG = "Main View Model"
    }
}