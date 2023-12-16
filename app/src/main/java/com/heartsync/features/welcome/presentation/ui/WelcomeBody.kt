package com.heartsync.features.welcome.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppClickableText
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.welcome.presentation.models.UiWelcomePage
import com.heartsync.features.welcome.presentation.viewmodels.WelcomeAction

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        WelcomeBody(
            gallery = listOf(
                UiWelcomePage(
                    photoUrl = "https://img.freepik.com/free-photo/vertical-image-gorgeous-fashionable-young-female-model-with-slim-body-posing-orange-studio_343059-3404.jpg?w=360&t=st=1702710263~exp=1702710863~hmac=7c86654d59c6fd3a1ec20e42c1cd8519ff444fead3a593feba1d42857a3a5e73",
                    title = "Title 1",
                    description = "Users going through a vetting process to ensure you never match with bots.",
                ),
                UiWelcomePage(
                    photoUrl = "https://a.d-cd.net/b38ff71s-960.jpg",
                    title = "Title 2",
                    description = "We match you with people that have a large array of similar interests.",
                ),
                UiWelcomePage(
                    photoUrl = "https://i.pinimg.com/736x/a1/07/4e/a1074ea33e2c1adfecb4b86487d7ea9d--male-fitness-photography-bodybuilding-photography.jpg",
                    title = "Title 3",
                    description = "Sign up today and enjoy the first month of premium benefits on us.",
                ),
            ),
            onAction = {},
        )
    }
}

private const val TAG_ANNOTATION = "annotation"
private const val ANNOTATION_SIGN_IN = "sign_in"

@Composable
fun WelcomeBody(
    gallery: List<UiWelcomePage>,
    modifier: Modifier = Modifier,
    onAction: (WelcomeAction) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Spacer(Modifier.height(8.dp))
        if (gallery.isNotEmpty()) {
            Gallery(
                pages = gallery,
                contentPadding = PaddingValues(horizontal = 40.dp),
            )
        } else {
            Box(
                modifier = Modifier
                    .size(width = 235.dp, height = 360.dp),
                contentAlignment = Alignment.Center,
            ) {
                AppIcon(
                    painter = painterResource(R.drawable.ic_logo),
                )
            }
        }
        Spacer(Modifier.height(42.dp))
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            text = stringResource(R.string.welcome_create_account),
            onClick = { onAction(WelcomeAction.OnCreateAccountClick) },
        )
        Spacer(Modifier.height(20.dp))
        val signInText = buildAnnotatedString {
            append(stringResource(R.string.welcome_exist_account))
            append(' ')
            pushStringAnnotation(TAG_ANNOTATION, ANNOTATION_SIGN_IN)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            ) {
                append(stringResource(R.string.welcome_sign_in))
            }
        }
        AppClickableText(
            text = signInText,
            onClick = { offset ->
                signInText.getStringAnnotations(TAG_ANNOTATION, offset, offset)
                    .firstOrNull()
                    ?.let { annotation ->
                        if (annotation.item == ANNOTATION_SIGN_IN) {
                            onAction(WelcomeAction.OnSignInCLick)
                        }
                    }
            },
        )
    }
}