package com.heartsync.features.authphone.smscode.presentation.ui.keyboard

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heartsync.core.ui.appcomponents.AppText
import com.heartsync.core.ui.theme.MediumCornerShape

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        DigitKeyboardButton(
            number = 9,
            onClick = {},
        )
        DigitKeyboardButton(
            //  icon = R.drawable.ic_backspace,
            onClick = {},
        )
        DigitKeyboardButton(
            //  icon = R.drawable.ic_backspace,
            enabled = false,
            onClick = {},
        )
    }
}

@Composable
fun DigitKeyboardButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    number: Int? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(MediumCornerShape)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = onClick,
            ),
    ) {
        when {
            icon != null -> {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(icon),
                    contentDescription = null,
                )
            }

            number != null -> {
                AppText(
                    modifier = Modifier.align(Alignment.Center),
                    text = number.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                )
            }
        }
    }
}