package com.heartsync.features.main.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route
import com.heartsync.features.main.presentation.models.UiBottomItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    appNavigator: AppNavigator,
    firebaseAuthProvider: FirebaseAuthProvider,
) : MviViewModel<MainState, MainEffect, MainAction>(MainState()) {

    val navigationChannel = appNavigator.navigationChannel
    private val currentNavItemFlow = MutableStateFlow<UiBottomItem?>(null)

    init {
        firebaseAuthProvider.isAuthentication()
            .onEach { authentication ->
                if (authentication) {

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
}