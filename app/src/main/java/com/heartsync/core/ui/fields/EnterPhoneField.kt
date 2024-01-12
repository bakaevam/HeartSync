package com.heartsync.core.ui.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.appcomponents.AppIcon
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.core.ui.tools.PhoneVisualTransformation
import com.heartsync.features.authphone.editnumber.presentation.viewmodels.EnterPhoneState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        EnterPhoneField(
            state = EnterPhoneState(),
            onPhoneChange = {},
        )
    }
}

@Composable
fun EnterPhoneField(
    state: EnterPhoneState,
    modifier: Modifier = Modifier,
    onPhoneChange: (String) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(MediumCornerShape)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MediumCornerShape
            )
            .background(color = MaterialTheme.colorScheme.background)
            .padding(17.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CountryCodeItem(modifier = Modifier.padding(end = 10.dp))
        Spacer(
            Modifier
                .width(1.dp)
                .height(18.dp)
                .background(color = MaterialTheme.colorScheme.outline),
        )
        BasicTextField(
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f),
            value = state.phone,
            visualTransformation = PhoneVisualTransformation,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                autoCorrect = false,
            ),
            onValueChange = onPhoneChange,
        )
    }
}

@Composable
fun CountryCodeItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AppIcon(
            modifier = Modifier.size(width = 20.dp, height = 15.dp),
            painter = painterResource(R.drawable.rus_flag),
        )
        AppText(
            text = stringResource(R.string.enter_phone_rus_prefix),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}