package com.heartsync.features.main.presentation.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.heartsync.core.tools.navigation.AppNavHost
import com.heartsync.core.tools.navigation.Destination
import com.heartsync.core.tools.navigation.NavigationEffects
import com.heartsync.core.tools.navigation.composable
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.features.authphone.editnumber.presentation.ui.EnterPhoneScreen
import com.heartsync.features.authphone.smscode.presentation.ui.SmsCodeScreen
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import com.heartsync.features.signup.presentation.ui.SignUpScreen
import com.heartsync.features.welcome.presentation.ui.WelcomeScreen

@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
) {
    val navController = rememberNavController()

    NavigationEffects(
        navigationChannel = mainViewModel.navigationChannel,
        navHostController = navController,
    )
    HeartSyncTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AppNavHost(
                navController = navController,
                startDestination = Destination.WelcomeScreen,
            ) {
                composable(destination = Destination.WelcomeScreen) {
                    WelcomeScreen()
                }
                composable(destination = Destination.SignUpScreen) {
                    SignUpScreen()
                }
                composable(destination = Destination.EnterPhoneScreen) {
                    EnterPhoneScreen()
                }
                composable(destination = Destination.SmsCodeScreen) {
                    SmsCodeScreen()
                }
            }
        }
    }
}