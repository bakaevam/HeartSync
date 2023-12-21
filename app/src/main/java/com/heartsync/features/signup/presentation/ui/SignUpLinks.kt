package com.heartsync.features.signup.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.features.signup.presentation.viewmodels.SignUpAction

@Composable
fun SignUpLinks(
    modifier: Modifier = Modifier,
    onAction: (SignUpAction) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AppText(
            modifier = Modifier.clickable(
                onClick = { onAction(SignUpAction.OnTermsOfUseClick) },
            ),
            text = stringResource(R.string.sign_up_terms_of_use),
        )
        AppText(
            modifier = Modifier.clickable(
                onClick = { onAction(SignUpAction.OnPrivacyPolicyClick) },
            ),
            text = stringResource(R.string.sign_up_privacy_policy),
        )
    }
}