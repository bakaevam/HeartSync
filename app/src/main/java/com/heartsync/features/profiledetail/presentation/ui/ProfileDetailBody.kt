package com.heartsync.features.profiledetail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.heartsync.R
import com.heartsync.core.ui.AvatarItem
import com.heartsync.core.ui.appcomponents.AppButton
import com.heartsync.core.ui.appcomponents.AppChooser
import com.heartsync.core.ui.appcomponents.AppDropDownField
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.appcomponents.AppTextField
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.tools.AppPreview
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailAction
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailState

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        ProfileDetailBody(
            state = ProfileDetailState(),
            onAction = {},
        )
    }
}

@Composable
fun ProfileDetailBody(
    state: ProfileDetailState,
    modifier: Modifier = Modifier,
    onAction: (ProfileDetailAction) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp, vertical = 25.dp),
    ) {
        AppText(
            text = stringResource(R.string.profile_detail_title),
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
        )
        Spacer(Modifier.height(24.dp))
        AvatarItem(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            avatarUrl = state.avatar,
            onEditClick = { onAction(ProfileDetailAction.OnEditAvatarClick) },
        )
        Spacer(Modifier.height(24.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.name,
            singleLine = true,
            label = stringResource(R.string.profile_detail_name_placeholder),
            placeholder = stringResource(R.string.profile_detail_name_placeholder),
            onValueChange = { password -> onAction(ProfileDetailAction.OnNameChange(password)) },
        )
        Spacer(Modifier.height(16.dp))
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            value = state.lastName,
            singleLine = true,
            label = stringResource(R.string.profile_detail_lastname_placeholder),
            placeholder = stringResource(R.string.profile_detail_lastname_placeholder),
            onValueChange = { password -> onAction(ProfileDetailAction.OnLastnameChange(password)) },
        )
        Spacer(Modifier.height(16.dp))
        AppDropDownField(
            value = state.birthday,
            placeholder = stringResource(R.string.profile_detail_birthday_placeholder),
            icon = painterResource(R.drawable.ic_calendar),
            onClick = { onAction(ProfileDetailAction.OnBirthdayClick) },
        )
        Spacer(Modifier.height(16.dp))
        AppChooser(
            items = state.genders,
            selectId = state.selectedGender,
            onClick = { id -> onAction(ProfileDetailAction.OnGenderClick(id)) },
        )
        Spacer(Modifier.height(24.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.action_save),
            enabled = state.saveEnabled,
            onClick = { onAction(ProfileDetailAction.OnSaveClick) },
        )
    }
}