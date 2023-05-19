package com.github.bkmbigo.mlkitsample.ui.components.translation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationLanguageOption
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationScreenState

@Composable
fun TranslationLanguageHeader(
    state: TranslationScreenState,
    showLanguageSelectionDialog: (TranslationLanguageOption) -> Unit,
    onReverseLanguages: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextButton(
            onClick = {
                showLanguageSelectionDialog(TranslationLanguageOption.ORIGINAL_LANGUAGE)
            },
            modifier = Modifier
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = stringResource(
                    id = state.originalLanguage?.let {
                        it.string
                    } ?: R.string.label_select_language
                ),
                fontSize = 16.sp,
            )
        }

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        IconButton(
            onClick = {
                onReverseLanguages()
            }
        ) {
            Icon(
                imageVector = Icons.Default.SwapHoriz,
                contentDescription = null
            )
        }

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        TextButton(
            onClick = {
                showLanguageSelectionDialog(TranslationLanguageOption.TARGET_LANGUAGE)
            }
        ) {
            Text(
                text = stringResource(
                    id = state.targetLanguage?.let {
                        it.string
                    } ?: R.string.label_select_language
                ),
                fontSize = 16.sp,
                modifier = Modifier.padding(vertical = 6.dp)
            )
        }
    }
}