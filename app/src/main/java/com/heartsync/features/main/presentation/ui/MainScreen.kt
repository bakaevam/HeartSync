package com.heartsync.features.main.presentation.ui

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.showToast
import com.heartsync.core.tools.navigation.AppNavHost
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.tools.navigation.NavigationEffects
import com.heartsync.core.tools.navigation.composable
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.features.authphone.editnumber.presentation.ui.EnterPhoneScreen
import com.heartsync.features.authphone.enteremail.presentation.ui.EnterEmailScreen
import com.heartsync.features.authphone.smscode.presentation.ui.SmsCodeScreen
import com.heartsync.features.discovery.presentation.ui.DiscoveryScreen
import com.heartsync.features.main.presentation.viewmodels.MainAction
import com.heartsync.features.main.presentation.viewmodels.MainEffect
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import com.heartsync.features.signup.presentation.ui.SignUpScreen
import com.heartsync.features.welcome.presentation.ui.WelcomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
) {
    val state by viewModel.state.collectAsState()
    val navController = rememberNavController()

    val activity = LocalContext.current as Activity
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is MainEffect.ShowError -> activity.showToast(effect.message)
        }
    }

    NavigationEffects(
        navigationChannel = viewModel.navigationChannel,
        navHostController = navController,
    )
    HeartSyncTheme {
        Scaffold(
            bottomBar = {
                if (state.bottomBarVisible) {
                    BottomNavBar(
                        items = state.bottomNavItems,
                        currentNavItem = state.currentNavItem,
                        modifier = Modifier.fillMaxWidth(),
                        onItemClick = { uiBottomItem ->
                            viewModel.onAction(MainAction.OnNavItemClick(uiBottomItem))
                        },
                    )
                }
            }
        ) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background,
            ) {
                AppNavHost(
                    navController = navController,
                    startDestination = Destination.WelcomeScreen,
                ) {
                    composable(destination = Destination.WelcomeScreen) {
                        viewModel.onAction(MainAction.OnNavigateWelcome)
                        WelcomeScreen()
                    }
                    composable(destination = Destination.SignUpScreen) {
                        SignUpScreen()
                    }
                    composable(destination = Destination.EnterPhoneScreen) {
                        EnterPhoneScreen()
                    }
                    composable(destination = Destination.EnterEmailScreen) {
                        EnterEmailScreen()
                    }
                    composable(destination = Destination.SmsCodeScreen) {
                        SmsCodeScreen()
                    }
                    composable(destination = Destination.DiscoveryScreen) {
                        viewModel.onAction(MainAction.OnNavigateDiscovery)
                        DiscoveryScreen()
                    }
                }
            }
        }
    }
}