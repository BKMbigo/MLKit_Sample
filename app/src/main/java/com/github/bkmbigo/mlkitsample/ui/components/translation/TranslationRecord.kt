package com.github.bkmbigo.mlkitsample.ui.components.translation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.LanguagePickerDialog
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.DownloadableLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.rememberDownloadableLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.screens.text.translation.TranslatedText
import com.github.bkmbigo.mlkitsample.ui.screens.text.translation.TranslationRecordState
import com.github.bkmbigo.mlkitsample.ui.screens.text.translation.TranslationScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import kotlinx.coroutines.launch

@Composable
fun TranslationRecord(
    state: TranslationRecordState,
    dialogState: DownloadableLanguageDialogState<TranslationLanguageView>,
    modifier: Modifier = Modifier,
    onCopyContent: (String) -> Unit = {},
    onShareContent: (String) -> Unit = {},
    onTranslateText: suspend (TranslationLanguageView, TranslationLanguageView, String) -> Unit = { _, _, _ -> },
    onLanguageDownloaded: suspend (TranslationLanguageView) -> Unit = {},
    onLanguageDeleted: suspend (TranslationLanguageView) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var showLanguagePickerDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            Text(
                text = stringResource(id = state.originalLanguage.languageView.string),
                textAlign = TextAlign.Center,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.originalText,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(),
            fontWeight = FontWeight.SemiBold,
            fontSize = 19.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        state.translations.forEach { translation ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        )
                ) {
                    Text(
                        text = stringResource(id = translation.languageView.languageView.string),
                        fontSize = 15.sp
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = translation.text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    IconButton(
                        onClick = { onCopyContent(translation.text) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = { onShareContent(translation.text) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = null
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { showLanguagePickerDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = stringResource(id = R.string.placeholder_add_translation)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

    }

    if (showLanguagePickerDialog) {
        LanguagePickerDialog(
            state = dialogState,
            heading = {
                Text(
                    text = stringResource(id = R.string.label_select_language),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            },
            onDismissDialog = { showLanguagePickerDialog = false },
            onLanguageChosen = {
                coroutineScope.launch {
                    onTranslateText(state.originalLanguage, it, state.originalText)
                    showLanguagePickerDialog = false
                }
            },
            onLanguageDeleted = onLanguageDeleted,
            onLanguageDownloaded = onLanguageDownloaded,
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewTranslationRecord() {

    val dialogState = rememberDownloadableLanguageDialogState(state = TranslationScreenState())

    MLKitSampleTheme {
        Scaffold {
            Column {
                TranslationRecord(
                    state = TranslationRecordState(
                        originalText = "Good Morning!",
                        originalLanguage = TranslationLanguageView.ENGLISH,
                        translations = listOf(
                            TranslatedText(
                                languageView = TranslationLanguageView.SWAHILI,
                                text = "Habari ya asubuhi!"
                            )
                        )
                    ),
                    dialogState = dialogState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )

                TranslationRecord(
                    state = TranslationRecordState(
                        originalText = "Sorry!",
                        originalLanguage = TranslationLanguageView.ENGLISH,
                        translations = listOf(
                            TranslatedText(
                                languageView = TranslationLanguageView.SPANISH,
                                text = "Por favor!"
                            ),
                            TranslatedText(
                                languageView = TranslationLanguageView.SWAHILI,
                                text = "Pole!"
                            )
                        )
                    ),
                    dialogState = dialogState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}