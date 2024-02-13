package com.heartsync.features.cabinet.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetAction
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetState

@Composable
fun CabinetBody(
    state: CabinetState,
    modifier: Modifier = Modifier,
    onAction: (CabinetAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        val profileData = state.uiProfileData
        if (profileData != null) {
            AppText(
                text = stringResource(R.string.cabinet_profile_title),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            AppText(text = profileData.name)
            AppText(text = profileData.lastName)
            AppText(text = profileData.birthday)
        }
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.cabinet_sign_out),
            type = Type.OUTLINE,
            onClick = { onAction(CabinetAction.OnSignOutClick) },
        )
    }
}