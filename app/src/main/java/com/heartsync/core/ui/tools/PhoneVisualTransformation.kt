package com.heartsync.core.ui.tools

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

object PhoneVisualTransformation : VisualTransformation {

    private val mapping = object : OffsetMapping {

        override fun originalToTransformed(offset: Int): Int {
            val result = when {
                offset <= 0 -> offset
                offset <= 2 -> offset + 1
                offset <= 6 -> offset + 3
                offset <= 8 -> offset + 4
                else -> offset + 5
            }
            return result
        }

        override fun transformedToOriginal(offset: Int): Int {
            val result = when {
                offset <= 3 -> offset
                offset <= 8 -> offset - 2
                offset <= 13 -> offset - 3
                offset <= 16 -> offset - 5
                else -> offset - 6
            }
            return result
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val newText = buildAnnotatedString {
            text.forEachIndexed { index, char ->
                when (index) {
                    0 -> append("(")
                }
                append(char)
                when (index) {
                    2 -> append(") ")
                    5, 7 -> append(' ')
                }
            }
        }

        return TransformedText(newText, mapping)
    }
}