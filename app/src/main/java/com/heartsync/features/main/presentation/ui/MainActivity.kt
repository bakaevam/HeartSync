package com.heartsync.features.main.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.firebase.Firebase
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.dynamicLinks
import com.heartsync.features.main.presentation.viewmodels.MainAction
import com.heartsync.features.main.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) {
        viewModel.onAction(MainAction.OnPermissionGrant(it))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(viewModel = viewModel)
        }
        handleDynamicLinks()
    }

    fun launchPermissions(permissions: Array<String>) {
        permissionLauncher.launch(permissions)
    }

    fun shouldShowPermissionRational(permission: String): Boolean =
        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

    private fun handleDynamicLinks() {
        Firebase.dynamicLinks
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData: PendingDynamicLinkData? ->
                if (pendingDynamicLinkData != null) {
                    val deepLink = pendingDynamicLinkData.link
                    viewModel.onAction(MainAction.OnHandleDeeplink(deepLink))
                }
            }
            .addOnFailureListener(this) { e ->
                Log.w(TAG, "getDynamicLink:onFailure", e)
            }
    }

    private companion object {
        private const val TAG = "Main Activity"
    }
}
