package com.heartsync.features.camera.presentation.ui

import android.content.Context
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.camera.presentation.viewmodels.CameraAction
import com.heartsync.features.camera.presentation.viewmodels.CameraState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        Camera(
            state = CameraState(),
            onAction = {},
        )
    }
}

@Composable
fun Camera(
    state: CameraState,
    modifier: Modifier = Modifier,
    onAction: (CameraAction) -> Unit,
) {

    val lifecycleOwner = LocalLifecycleOwner.current
    // Setup Basic Camera
    val preview = Preview.Builder().build()
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    // Image Capture Functionality
    val imageCapture = state.imageCapture
    LaunchedEffect(state.cameraSelector) {
        val cameraProvider = getCameraProvider(context)
        cameraProvider.unbindAll()
        if (state.cameraSelector != null) {
            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner,
                state.cameraSelector,
                preview,
                imageCapture,
            )

            preview.setSurfaceProvider(previewView.surfaceProvider)
            previewView.scaleType = PreviewView.ScaleType.FILL_CENTER
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { previewView },
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Spacer(Modifier.size(72.dp))
            AppIcon(
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = false),
                    onClick = { onAction(CameraAction.OnTakePictureClick) },
                ),
                painter = painterResource(R.drawable.ic_take_photo),
            )
            AppIcon(
                modifier = Modifier.clickable(
                    interactionSource = MutableInteractionSource(),
                    indication = rememberRipple(bounded = false),
                    onClick = { onAction(CameraAction.OnChangeLensClick) },
                ),
                painter = painterResource(R.drawable.ic_change_lens),
            )
        }
    }
}

suspend fun getCameraProvider(context: Context): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(context).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(context))
        }
    }