package com.heartsync.features.main.presentation.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route
import com.heartsync.features.authphone.enteremail.domain.EnterEmailRepository
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider
import com.heartsync.features.main.data.providers.auth.FirebaseAuthProvider.Companion.KEY_EMAIL
import com.heartsync.features.main.presentation.models.UiBottomItem
import com.heartsync.features.main.presentation.models.UiNavItem
import com.heartsync.features.profiledetail.domain.repository.UserRepository
import com.heartsync.features.signup.domain.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    appNavigator: AppNavigator,
    authRepository: AuthRepository,
    private val enterEmailRepository: EnterEmailRepository,
    private val userRepository: UserRepository,
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
                bottomItem = UiBottomItem.CABINET,
                badgeText = null,
            ),
        ),
    )
) {

    val navigationChannel = appNavigator.navigationChannel
    private val currentNavItemFlow = MutableStateFlow<UiBottomItem?>(null)

    init {
        authRepository.isAuthentication()
            .onEach { authentication ->
                if (authentication) {
                    currentNavItemFlow.value = UiBottomItem.DISCOVERY
                    userRepository.initChats()
                } else {
                    currentNavItemFlow.value = null
                    appNavigator.tryNavigateTo(
                        route = Route.WELCOME.key,
                        popUpToRoute = null,
                        inclusive = false,
                        isSingleTop = false,
                        popBackStack = true,
                    )
                }
                Log.e("Main View Model", authentication.toString())
            }
            .launchIn(viewModelScope)

        authRepository.observeNewUser()
            .onEach { newUser ->
                if (newUser != null) {
                    appNavigator.tryNavigateTo(
                        route = Route.PROFILE_DETAIL.key,
                    )
                    authRepository.resetNewUser()
                }
            }
            .launchIn(viewModelScope)

        currentNavItemFlow
            .onEach { tab ->
                setState { copy(currentNavItem = tab) }
                when (tab) {
                    UiBottomItem.DISCOVERY -> appNavigator.tryNavigateTo(
                        route = Route.DISCOVERY.key,
                        inclusive = true,
                        isSingleTop = true,
                        popBackStack = true,
                    )

                    UiBottomItem.MATCHERS -> appNavigator.tryNavigateTo(
                        route = Route.MATCHES.key,
                        inclusive = true,
                        isSingleTop = true,
                        popBackStack = true,
                    )

                    UiBottomItem.MESSAGES -> appNavigator.tryNavigateTo(
                        route = Route.MESSAGES.key,
                        inclusive = true,
                        isSingleTop = true,
                        popBackStack = true,
                    )

                    UiBottomItem.CABINET -> appNavigator.tryNavigateTo(
                        route = Route.CABINET.key,
                        inclusive = true,
                        isSingleTop = true,
                        popBackStack = true,
                    )

                    else -> {}
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onAction(action: MainAction) = when (action) {
        is MainAction.OnNavItemClick -> onNavItemClick(action)
        is MainAction.OnNavigateDiscovery -> changeBottomNavBarVisibility(true)
        is MainAction.OnNavigateWelcome -> changeBottomNavBarVisibility(false)
        is MainAction.OnHandleDeeplink -> handleDeeplink(action)
        is MainAction.OnNavigateProfileDetail -> changeBottomNavBarVisibility(false)
        is MainAction.OnPermissionGrant -> {}
    }

    private fun onNavItemClick(action: MainAction.OnNavItemClick) {
        currentNavItemFlow.value = action.bottomItem
    }

    private fun changeBottomNavBarVisibility(visible: Boolean) {
        setState { copy(bottomBarVisible = visible) }
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