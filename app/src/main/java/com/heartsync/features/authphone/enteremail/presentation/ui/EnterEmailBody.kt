package com.heartsync.features.authphone.enteremail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.AppTextField
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailAction
import com.heartsync.features.authphone.enteremail.presentation.viewmodels.EnterEmailState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        EnterEmailBody(
            state = EnterEmailState(),
            onAction = {},
        )
    }
}

@Composable
fun EnterEmailBody(
    state: EnterEmailState,
    modifier: Modifier = Modifier,
    onAction: (EnterEmailAction) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 40.dp, vertical = 24.dp),
    ) {
        AppButton(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.End),
            text = stringResource(R.string.navigation_back),
            type = Type.OUTLINE,
            onClick = { onAction(EnterEmailAction.OnBackClick) },
        )
        Spacer(Modifier.height(40.dp))
        AppText(
            text = stringResource(R.string.enter_email_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(Modifier.height(4.dp))
        AppText(
            text = state.description,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(24.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.email,
            singleLine = true,
            autoFocus = true,
            placeholder = stringResource(R.string.enter_email_placeholder),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.None,
            ),
            onValueChange = { email -> onAction(EnterEmailAction.OnEmailChange(email)) },
        )
        Spacer(Modifier.height(4.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.password,
            singleLine = true,
            placeholder = stringResource(R.string.enter_email_password),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                autoCorrect = false,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.None,
            ),
            onValueChange = { password -> onAction(EnterEmailAction.OnPasswordChange(password)) },
        )
        if (state.repeatPasswordVisible) {
            Spacer(Modifier.height(4.dp))
            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.repeatPassword,
                singleLine = true,
                placeholder = stringResource(R.string.enter_email_repeat_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    autoCorrect = false,
                    imeAction = ImeAction.Done,
                    capitalization = KeyboardCapitalization.None,
                ),
                onValueChange = { repeatPassword ->
                    onAction(
                        EnterEmailAction.OnRepeatPasswordChange(
                            repeatPassword
                        )
                    )
                },
            )
        }
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = stringResource(R.string.enter_phone_continue),
            enabled = state.continueEnabled,
            onClick = { onAction(EnterEmailAction.OnContinueClick) },
        )
    }
}