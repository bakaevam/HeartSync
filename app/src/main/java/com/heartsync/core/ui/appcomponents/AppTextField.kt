package com.heartsync.core.ui.appcomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.theme.HeartSyncTheme
import com.heartsync.core.ui.theme.MediumCornerShape
import com.heartsync.core.ui.tools.AppPreview

@AppPreview
@Composable
private fun Preview() {
    HeartSyncTheme {
        AppTextField(
            value = "son@mail.ru",
            onValueChange = {},
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTextField(
    value: String,
    modifier: Modifier = Modifier,
    label: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    enabled: Boolean = true,
    autoFocus: Boolean = false,
    placeholder: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    BasicTextField(
        modifier = modifier.focusRequester(focusRequester),
        value = value,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        decorationBox = { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                contentPadding = PaddingValues(17.dp),
                container = {
                    Box(
                        modifier = Modifier
                            .clip(MediumCornerShape)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = MediumCornerShape,
                            )
                            .background(color = MaterialTheme.colorScheme.background),
                    )
                },
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                interactionSource = interactionSource,
                visualTransformation = visualTransformation,
                placeholder = placeholder?.let { text ->
                    {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.TopStart,
                        ) {
                            AppText(
                                modifier = Modifier.padding(vertical = 12.dp),
                                text = text,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.outline
                                ),
                            )
                        }
                    }
                },
                label = label?.let { text ->
                    {
                        AppText(
                            text = text,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Unspecified,
                            ),
                            maxLines = 1,
                        )
                    }
                },
            )
        },
        onValueChange = onValueChange,
    )
    LaunchedEffect(key1 = autoFocus) {
        if (autoFocus) {
            focusRequester.requestFocus()
        }
    }
}