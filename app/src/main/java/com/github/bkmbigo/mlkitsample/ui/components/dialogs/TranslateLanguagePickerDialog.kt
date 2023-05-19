package com.github.bkmbigo.mlkitsample.ui.components.dialogs

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoDelete
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.TranslationLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.TranslationLanguageState
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.rememberTranslationLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.screens.text.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationLanguageOption
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TranslationLanguagePickerDialog(
    state: TranslationLanguageDialogState,
    onDismissDialog: () -> Unit,
    onLanguageChosen: (LanguageView) -> Unit = {},
    onLanguageDeleted: suspend (LanguageView) -> Unit = {},
    onLanguageDownloaded: suspend (LanguageView) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    val downloadedLanguages by remember(state) {
        derivedStateOf {
            state.languages.filter { it.translationLanguageState == TranslationLanguageState.DOWNLOADED }
        }
    }

    val availableLanguages by remember(state) {
        derivedStateOf {
            state.languages.filter { it.translationLanguageState != TranslationLanguageState.DOWNLOADED }
        }
    }

    val isSafeToCloseDialog by remember(state) {
        derivedStateOf {
            state.languages.none { it.translationLanguageState == TranslationLanguageState.DOWNLOADING || it.translationLanguageState == TranslationLanguageState.DELETING }
        }
    }

    var showConfirmCloseDialog by remember { mutableStateOf(false) }


    Dialog(
        onDismissRequest = {
            if (isSafeToCloseDialog) {
                onDismissDialog()
            } else {
                showConfirmCloseDialog = true
            }
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(15.dp)
                )
        ) {
            IconButton(
                onClick = {
                    if (isSafeToCloseDialog) {
                        onDismissDialog()
                    } else {
                        showConfirmCloseDialog = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 4.dp, end = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(
                    id = when (state.languageOption) {
                        TranslationLanguageOption.ORIGINAL_LANGUAGE -> R.string.label_select_original_language
                        TranslationLanguageOption.TARGET_LANGUAGE -> R.string.label_select_target_language
                    }
                ),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.label_downloaded_languages),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 6.dp)
            )

            AnimatedContent(
                targetState = downloadedLanguages.isNotEmpty(),
                transitionSpec = {
                    slideInHorizontally() with slideOutHorizontally()
                },
                label = ""
            ) {
                when (it) {
                    true -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, true),
                            contentPadding = PaddingValues(vertical = 4.dp)
                        ) {
                            items(downloadedLanguages) { translatableLanguage ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 2.dp, horizontal = 4.dp)
                                ) {

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        TextButton(
                                            onClick = {
                                                onLanguageChosen(translatableLanguage.languageView)
                                            },
                                            modifier = Modifier
                                                .padding(vertical = 4.dp, horizontal = 6.dp)
                                                .weight(1f, true)
                                        ) {
                                            Text(
                                                text = stringResource(id = translatableLanguage.languageView.string),
                                                fontSize = 17.sp,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        onLanguageDeleted(translatableLanguage.languageView)
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.DeleteForever,
                                                    contentDescription = null
                                                )
                                            }
                                        }
                                    }

                                    Divider(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    false -> {
                        Text(
                            text = stringResource(id = R.string.label_no_downloaded_languages),
                            modifier = Modifier
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontSize = 17.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.label_available_languages),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 6.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(availableLanguages) { translatableLanguage ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 2.dp, horizontal = 4.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = translatableLanguage.languageView.string),
                                fontSize = 17.sp,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .padding(vertical = 4.dp)
                                    .weight(1f, true)
                            )

                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        when (translatableLanguage.translationLanguageState) {
                                            TranslationLanguageState.AVAILABLE -> {
                                                onLanguageDownloaded(translatableLanguage.languageView)
                                            }

                                            TranslationLanguageState.ERROR -> {
                                                onLanguageDownloaded(translatableLanguage.languageView)
                                            }

                                            else -> {}
                                        }
                                    }
                                },
                                enabled = !(translatableLanguage.translationLanguageState == TranslationLanguageState.DOWNLOADING ||
                                        translatableLanguage.translationLanguageState == TranslationLanguageState.DELETING)
                            ) {
                                Icon(
                                    imageVector = when (translatableLanguage.translationLanguageState) {
                                        TranslationLanguageState.AVAILABLE -> Icons.Default.DownloadForOffline
                                        TranslationLanguageState.DOWNLOADING -> Icons.Default.Downloading
                                        TranslationLanguageState.DOWNLOADED -> Icons.Default.DownloadDone
                                        TranslationLanguageState.ERROR -> Icons.Default.Error
                                        TranslationLanguageState.DELETING -> Icons.Default.AutoDelete
                                    },
                                    contentDescription = null,
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = translatableLanguage.translationLanguageState == TranslationLanguageState.DOWNLOADING ||
                                    translatableLanguage.translationLanguageState == TranslationLanguageState.DELETING,
                            enter = slideInVertically() + expandHorizontally(expandFrom = Alignment.CenterHorizontally),
                            exit = slideOutVertically() + shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally)
                        ) {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }


        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }

    AnimatedVisibility(
        visible = showConfirmCloseDialog,
        enter = expandVertically(expandFrom = Alignment.CenterVertically) +
                expandHorizontally(expandFrom = Alignment.CenterHorizontally),
        exit = shrinkHorizontally(shrinkTowards = Alignment.CenterHorizontally) +
                shrinkVertically(shrinkTowards = Alignment.CenterVertically)
    ) {
        AlertDialog(
            onDismissRequest = { showConfirmCloseDialog = false },
            title = {
                Text(
                    text = stringResource(id = R.string.label_confirm_close)
                )
            },
            text = {
                Text(
                    text = stringResource(id = R.string.label_info_confirm_close_translate_language)
                )
            },
            dismissButton = {
                TextButton(
                    onClick = { showConfirmCloseDialog = false }
                ) {
                    Text(text = stringResource(id = R.string.label_no))
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismissDialog()
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.label_yes)
                    )
                }
            }
        )
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun PreviewLanguageTranslationPickerDialog() {
    var showDialog by remember { mutableStateOf(false) }
    var originalLanguage by remember { mutableStateOf<LanguageView?>(null) }
    var downloadedLanguages by remember {
        mutableStateOf(
            persistentListOf(
                LanguageView.ENGLISH,
                LanguageView.SWAHILI,
            )
        )
    }

    val state =
        rememberTranslationLanguageDialogState(
                languageOption = TranslationLanguageOption.ORIGINAL_LANGUAGE,
                downloadedLanguages = downloadedLanguages
            )

    MLKitSampleTheme {
        Scaffold {

            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Original Language: ${originalLanguage?.let { stringResource(id = it.string) } ?: "Unknown"}",
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {
                            showDialog = true
                        }
                    ) {
                        Text(
                            text = "Show Dialog"
                        )
                    }
                }


                if (showDialog) {
                    TranslationLanguagePickerDialog(
                        state = state,
                        onDismissDialog = {
                            showDialog = false
                        },
                        onLanguageChosen = {
                            originalLanguage = it
                        },
                        onLanguageDownloaded = {
                            downloadedLanguages = downloadedLanguages.add(it)
                        },
                        onLanguageDeleted = {
                            downloadedLanguages = downloadedLanguages.remove(it)
                        }
                    )
                }
            }
        }
    }
}