package com.heartsync.features.profiledetail.presentation.ui

import android.app.Activity
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.heartsync.core.tools.extention.CollectEffect
import com.heartsync.core.tools.extention.showToast
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailAction
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailEffect
import com.heartsync.features.profiledetail.presentation.viewmodels.ProfileDetailViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileDetailScreen(
    viewModel: ProfileDetailViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    ProfileDetailBody(
        state = state,
        onAction = viewModel::onAction,
    )

    val activity = LocalContext.current as Activity
    CollectEffect(source = viewModel.effect) { effect ->
        when (effect) {
            is ProfileDetailEffect.ShowBirthdayPicker -> {
                val picker = DatePickerDialog(
                    activity,
                    { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                        viewModel.onAction(
                            ProfileDetailAction.OnBirthdayConfirm(
                                year,
                                month,
                                dayOfMonth
                            )
                        )
                    },
                    effect.year,
                    effect.month,
                    effect.day,
                )
                picker.datePicker.apply {
                    maxDate = effect.maxDay
                }
                picker.show()
            }

            is ProfileDetailEffect.ShowError -> activity.showToast(effect.message)
        }
    }
}