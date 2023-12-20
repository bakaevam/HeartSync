package com.heartsync.features.signup.presentation.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.features.signup.presentation.models.SocialSignUp
import com.heartsync.features.signup.presentation.viewmodels.SignUpAction

@Composable
fun SocialSignUp(
    socialSignUp: List<SocialSignUp>,
    modifier: Modifier = Modifier,
    onAction: (SignUpAction) -> Unit,
) {
    Row(modifier = modifier) {
        socialSignUp.forEach { social ->
            val icon = when (social) {
                SocialSignUp.GOOGLE -> R.drawable.ic_google
            }
            AppButton(
                modifier = Modifier.size(64.dp),
                icon = painterResource(icon),
                type = Type.OUTLINE,
                onClick = { onAction(SignUpAction.OnSocialSignUpClick(social)) },
            )
        }
    }
}