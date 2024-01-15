package com.heartsync.features.authphone.editnumber.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.core.ui.fields.EnterPhoneField
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneAction
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        EnterPhoneBody(
            state = EnterPhoneState(),
            onAction = {},
        )
    }
}

@Composable
fun EnterPhoneBody(
    state: EnterPhoneState,
    modifier: Modifier = Modifier,
    onAction: (EnterPhoneAction) -> Unit,
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
            onClick = { onAction(EnterPhoneAction.OnBackClick) },
        )
        Spacer(Modifier.height(40.dp))
        AppText(
            text = stringResource(R.string.enter_phone_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(Modifier.height(4.dp))
        AppText(
            text = stringResource(R.string.enter_phone_description),
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(Modifier.height(24.dp))
        EnterPhoneField(
            modifier = Modifier.fillMaxWidth(),
            state = state,
            onPhoneChange = { phone -> onAction(EnterPhoneAction.OnPhoneChange(phone)) },
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            text = stringResource(R.string.enter_phone_continue),
            enabled = state.continueEnabled,
            onClick = { onAction(EnterPhoneAction.OnContinueClick) },
        )
    }
}