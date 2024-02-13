package com.heartsync.features.cabinet.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.Type
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetAction
import com.heartsync.features.cabinet.presentation.viewmodels.CabinetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CabinetScreen(
    viewModel: CabinetViewModel = koinViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 24.dp),
    ) {
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.cabinet_sign_out),
            type = Type.OUTLINE,
            onClick = { viewModel.onAction(CabinetAction.OnSignOutClick) },
        )
    }
}