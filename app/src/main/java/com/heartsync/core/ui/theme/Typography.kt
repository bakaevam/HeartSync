package com.heartsync.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.heartsync.R

val skModernist = FontFamily(
    Font(R.font.sk_modernist_mono, FontWeight.Normal),
    Font(R.font.sk_modernist_bold, FontWeight.Bold),
    Font(R.font.sk_modernist_regular, FontWeight.Medium),
)

val Typography = Typography(
    headlineMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = skModernist,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
    ),
)