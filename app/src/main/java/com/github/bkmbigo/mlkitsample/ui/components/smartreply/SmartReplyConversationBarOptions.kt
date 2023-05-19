package com.github.bkmbigo.mlkitsample.ui.components.smartreply

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SmartReplyConversationBarOptions(
    val boxStartPadding: Dp,
    val barMaxWidth: Float,
    val barCornerRadius: Dp,
    val boxContentPadding: PaddingValues,
    val localUserBoxBackground: Color,
    val remoteUserBoxBackground: Color,
    val localUserTextColor: Color,
    val remoteUserTextColor: Color,
)

@Composable
fun rememberSmartReplyConversationBarOptions(
    boxStartPadding: Dp = 12.dp,
    barMaxWidth: Float = 0.80f,
    barCornerRadius: Dp = 8.dp,
    boxContentPadding: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 12.dp),
    localUserBoxBackground: Color = MaterialTheme.colorScheme.primaryContainer,
    remoteUserBoxBackground: Color = MaterialTheme.colorScheme.secondaryContainer,
    localUserTextColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    remoteUserTextColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
) = remember(
    boxStartPadding,
    barMaxWidth,
    barCornerRadius,
    localUserBoxBackground,
    remoteUserBoxBackground,
    localUserTextColor,
    remoteUserTextColor,
    boxContentPadding
) {
    SmartReplyConversationBarOptions(
        boxStartPadding = boxStartPadding,
        barMaxWidth = barMaxWidth,
        barCornerRadius = barCornerRadius,
        localUserBoxBackground = localUserBoxBackground,
        remoteUserBoxBackground = remoteUserBoxBackground,
        localUserTextColor = localUserTextColor,
        remoteUserTextColor = remoteUserTextColor,
        boxContentPadding = boxContentPadding

    )
}

