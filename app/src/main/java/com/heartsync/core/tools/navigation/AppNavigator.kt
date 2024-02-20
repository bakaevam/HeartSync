package com.heartsync.core.tools.navigation

import kotlinx.coroutines.channels.Channel

interface AppNavigator {

    val navigationChannel: Channel<NavigationIntent>

    suspend fun navigateBack(
        route: String? = null,
        inclusive: Boolean = false,
    )

    fun tryNavigateBack(
        route: String? = null,
        inclusive: Boolean = false,
        data: Map<String, Any?>? = null,
    )

    suspend fun navigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
    )

    fun tryNavigateTo(
        route: String,
        popUpToRoute: String? = null,
        inclusive: Boolean = false,
        isSingleTop: Boolean = false,
        popBackStack: Boolean = false,
    )
}

sealed class NavigationIntent {

    data class NavigateBack(
        val route: String? = null,
        val inclusive: Boolean = false,
        val data: Map<String, Any?>? = null,
    ) : NavigationIntent()

    data class NavigateTo(
        val route: String,
        val popUpToRoute: String? = null,
        val inclusive: Boolean = false,
        val isSingleTop: Boolean = false,
        val popBackStack: Boolean = false,
    ) : NavigationIntent()
}