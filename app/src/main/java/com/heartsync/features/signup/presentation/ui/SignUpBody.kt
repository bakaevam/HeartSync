package com.heartsync.features.signup.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.signup.presentation.viewmodels.SignUpAction
import com.heartsync.features.signup.presentation.viewmodels.SignUpState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        SignUpBody(
            state = SignUpState(
                socialSignUp = listOf(com.heartsync.features.signup.presentation.models.SocialSignUp.GOOGLE)
            ),
            onAction = {},
        )
    }
}

@Composable
fun SignUpBody(
    state: SignUpState,
    modifier: Modifier = Modifier,
    onAction: (SignUpAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AppIcon(painter = painterResource(R.drawable.ic_logo))
        Spacer(Modifier.height(40.dp))
        AppText(
            text = stringResource(R.string.sign_up_continue),
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(Modifier.height(24.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.sign_up_email),
            onClick = { onAction(SignUpAction.OnContinueWithEmailClick) },
        )
        Spacer(Modifier.height(16.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.sign_up_phone),
            type = Type.OUTLINE,
            onClick = { onAction(SignUpAction.OnUsePhoneClick) },
        )
        Spacer(Modifier.height(60.dp))
        if (state.socialSignUp.isNotEmpty()) {
            SignUpSocialTitle()
            Spacer(Modifier.height(24.dp))
            SocialSignUp(
                socialSignUp = state.socialSignUp,
                onAction = onAction,
            )
        }
        Spacer(Modifier.height(24.dp))
        SignUpLinks(onAction = onAction)
    }
}