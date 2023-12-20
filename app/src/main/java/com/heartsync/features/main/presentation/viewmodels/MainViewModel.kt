package com.heartsync.features.main.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.heartsync.core.base.MviViewModel
import com.heartsync.core.providers.auth.FirebaseAuthProvider
import com.heartsync.core.tools.navigation.AppNavigator
import com.heartsync.core.tools.navigation.Route
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    appNavigator: AppNavigator,
    firebaseAuthProvider: FirebaseAuthProvider,
) : MviViewModel<MainState, MainEffect, MainAction>(MainState()) {

    val navigationChannel = appNavigator.navigationChannel

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
    }

    override fun onAction(action: MainAction) {

    }
}