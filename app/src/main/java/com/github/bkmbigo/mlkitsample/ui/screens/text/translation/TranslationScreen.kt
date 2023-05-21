package com.github.bkmbigo.mlkitsample.ui.screens.text.translation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.MainNavGraph
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.TextInputTextField
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.LanguagePickerDialog
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.rememberDownloadableLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.components.translation.TranslationLanguageHeader
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageOption
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@MainNavGraph
@Destination
@Composable
fun TranslationScreen(
    originalLanguage: TranslationLanguageView? = null,
    text: String? = null,
    navigator: DestinationsNavigator
) {

    val modelManager = remember { RemoteModelManager.getInstance() }
    val downloadConditions = remember { DownloadConditions.Builder().requireWifi().build() }

    var state by remember {
        mutableStateOf(
            TranslationScreenState(
                originalLanguage = originalLanguage,
                originalText = TextFieldValue(text?.let { it } ?: "")
            )
        )
    }

    LaunchedEffect(Unit) {
        val list = modelManager
            .getDownloadedModels(TranslateRemoteModel::class.java)
            .await()
            .toList()
            .mapNotNull {
                TranslationLanguageView.getTranslationLanguageView(it.language)
            }
        state = state.copy(downloadedLanguages = list.toPersistentList())
    }

    TranslationScreenContent(
        state = state,
        onStateChanged = {
            state = it
        },
        onNavigateUp = {
           navigator.navigateUp()
        },
        onTranslateText = { originalLanguage: TranslationLanguageView, targetLanguage: TranslationLanguageView, text: String ->
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(originalLanguage.translationLanguage)
                .setTargetLanguage(targetLanguage.translationLanguage)
                .build()

            val translator = Translation.getClient(options)
            try {
                val translated = translator.translate(text).await()
                state = state.copy(
                    targetText = TextFieldValue(translated)
                )
            } catch (exception: MlKitException) {
                // TODO
            }
        },
        onCopyContent = {},
        onShareContent = {},
        onLanguageDeleted = { languageView ->
            val model = TranslateRemoteModel.Builder(languageView.translationLanguage).build()
            state = state.copy(
                deletingLanguages = state.deletingLanguages.add(languageView),
                errorLanguages = state.errorLanguages.remove(languageView)
            )
            try {
                modelManager.deleteDownloadedModel(model).await()
                val list = modelManager
                    .getDownloadedModels(TranslateRemoteModel::class.java)
                    .await()
                    .toList()
                    .mapNotNull {
                        TranslationLanguageView.getTranslationLanguageView(it.language)
                    }.toPersistentList()
                state = state.copy(
                    downloadedLanguages = list.toPersistentList(),
                    deletingLanguages = state.deletingLanguages.remove(languageView)
                )
            } catch (e: MlKitException) {
                state = state.copy(
                    deletingLanguages = state.deletingLanguages.remove(languageView),
                    errorLanguages = state.errorLanguages.add(languageView)
                )
            }

        },
        onLanguageDownloaded = { languageView ->
            val model = TranslateRemoteModel.Builder(languageView.translationLanguage).build()
            state = state.copy(
                downloadingLanguages = state.downloadingLanguages.add(languageView),
                errorLanguages = state.errorLanguages.remove(languageView)
            )
            try {
                modelManager.download(model, downloadConditions).await()
                val list = modelManager
                    .getDownloadedModels(TranslateRemoteModel::class.java)
                    .await()
                    .toList()
                    .mapNotNull {
                        TranslationLanguageView.getTranslationLanguageView(it.language)
                    }.toPersistentList()
                state = state.copy(
                    downloadedLanguages = list.toPersistentList(),
                    downloadingLanguages = state.downloadingLanguages.remove(languageView)
                )
            } catch (e: MlKitException) {
                state = state.copy(
                    downloadingLanguages = state.downloadingLanguages.remove(languageView),
                    errorLanguages = state.errorLanguages.add(languageView)
                )
            }
        }
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
private fun TranslationScreenContent(
    state: TranslationScreenState,
    onStateChanged: (TranslationScreenState) -> Unit,
    onNavigateUp: () -> Unit = {},
    onTranslateText: suspend (TranslationLanguageView, TranslationLanguageView, String) -> Unit = { _, _, _ -> },
    onCopyContent: (String) -> Unit = {},
    onShareContent: (String) -> Unit = {},
    onLanguageDeleted: suspend (TranslationLanguageView) -> Unit = {},
    onLanguageDownloaded: suspend (TranslationLanguageView) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    val isOriginalTextBlank by remember(state) {
        derivedStateOf { state.originalText.text.isBlank() }
    }
    val isTranslatedTextBlank by remember(state) {
        derivedStateOf { state.targetText.text.isBlank() }
    }

    var showSelectLanguageDialog by remember { mutableStateOf<TranslationLanguageOption?>(null) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { /*NO OP*/ },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.label_translation),
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f, true),
                    verticalArrangement = Arrangement.Center
                ) {

                    TranslationLanguageHeader(
                        state = state,
                        showLanguageSelectionDialog = {
                            showSelectLanguageDialog = it
                        },
                        onReverseLanguages = {
                            onStateChanged(
                                state.copy(
                                    originalLanguage = state.targetLanguage,
                                    targetLanguage = state.originalLanguage,
                                    originalText = state.targetText,
                                    targetText = state.originalText
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 45.dp)
                            .padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    AnimatedVisibility(
                        visible = state.originalLanguage != null && state.targetLanguage != null,
                        enter = expandVertically(expandFrom = Alignment.CenterVertically),
                        exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically),
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f, true),
                        ) {

                            Spacer(modifier = Modifier.height(8.dp))

                            TextInputTextField(
                                value = state.originalText,
                                onValueChange = {
                                    onStateChanged(
                                        state.copy(
                                            originalText = it
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.8f, true)
                                    .padding(horizontal = 12.dp),
                                topActions = {
                                    Spacer(modifier = Modifier.width(4.dp))

                                    Text(
                                        text = stringResource(id = R.string.placeholder_enter_text),
                                        fontWeight = FontWeight.Light,
                                        modifier = Modifier
                                            .padding(vertical = 4.dp)
                                            .padding(start = 4.dp)
                                    )

                                    Box(
                                        modifier = Modifier
                                            .weight(1f, true)
                                            .height(ButtonDefaults.MinHeight)
                                            .padding(vertical = 4.dp)
                                    )

                                    AnimatedVisibility(
                                        visible = !isOriginalTextBlank,
                                        enter = expandVertically() + expandHorizontally(),
                                        exit = shrinkVertically() + shrinkHorizontally()
                                    ) {
                                        IconButton(
                                            onClick = {
                                                onStateChanged(
                                                    state.copy(
                                                        originalText = TextFieldValue("")
                                                    )
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))

                                },
                                bottomActions = {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f, true)
                                            .height(ButtonDefaults.MinHeight)
                                    )

                                    AnimatedVisibility(
                                        visible = !isOriginalTextBlank,
                                        enter = expandVertically() + expandHorizontally(),
                                        exit = shrinkVertically() + shrinkHorizontally()
                                    ) {

                                        IconButton(
                                            onClick = {
                                                onCopyContent(state.originalText.text)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCopy,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    AnimatedVisibility(
                                        visible = !isOriginalTextBlank,
                                        enter = expandVertically() + expandHorizontally(),
                                        exit = shrinkVertically() + shrinkHorizontally()
                                    ) {
                                        IconButton(
                                            onClick = {
                                                onShareContent(state.originalText.text)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    AnimatedVisibility(
                                        visible = !isOriginalTextBlank,
                                        enter = slideInHorizontally { it / 8 },
                                        exit = slideOutHorizontally { it / 8 }
                                    ) {
                                        Button(
                                            onClick = {
                                                coroutineScope.launch {
                                                    if (state.originalLanguage != null && state.targetLanguage != null) {
                                                        onTranslateText(
                                                            state.originalLanguage,
                                                            state.targetLanguage,
                                                            state.originalText.text
                                                        )
                                                    }
                                                }
                                            }
                                        ) {
                                            Text(
                                                text = "Translate",
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier = Modifier
                                            .width(4.dp)
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            TextInputTextField(
                                value = state.targetText,
                                onValueChange = {
                                    onStateChanged(
                                        state.copy(targetText = it)
                                    )
                                },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(0.8f, true)
                                    .padding(horizontal = 12.dp),
                                topActions = {
                                    Spacer(modifier = Modifier.width(4.dp))

                                    Box(
                                        modifier = Modifier
                                            .weight(1f, true)
                                            .height(ButtonDefaults.MinHeight)
                                            .padding(vertical = 4.dp)
                                    )

                                    AnimatedVisibility(
                                        visible = !isTranslatedTextBlank,
                                        enter = slideInHorizontally { it / 2 },
                                        exit = slideOutHorizontally { it / 2 }
                                    ) {
                                        IconButton(
                                            onClick = {
                                                onStateChanged(
                                                    state.copy(targetText = TextFieldValue(""))
                                                )
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Clear,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))

                                },
                                bottomActions = {
                                    Box(
                                        modifier = Modifier
                                            .weight(1f, true)
                                            .height(ButtonDefaults.MinHeight)
                                    )

                                    AnimatedVisibility(
                                        visible = !isTranslatedTextBlank,
                                        enter = slideInHorizontally { it / 2 },
                                        exit = slideOutHorizontally { it / 2 }
                                    ) {

                                        IconButton(
                                            onClick = {
                                                onCopyContent(state.targetText.text)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ContentCopy,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    AnimatedVisibility(
                                        visible = !isTranslatedTextBlank,
                                        enter = slideInHorizontally { it / 4 },
                                        exit = slideOutHorizontally { it / 4 }
                                    ) {
                                        IconButton(
                                            onClick = {
                                                onShareContent(state.targetText.text)
                                            }
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Share,
                                                contentDescription = null
                                            )
                                        }
                                    }

                                    Spacer(
                                        modifier = Modifier
                                            .width(4.dp)
                                    )
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                        }
                    }
                }
            }

            AnimatedVisibility(
                visible = showSelectLanguageDialog != null,
                enter = expandVertically(expandFrom = Alignment.CenterVertically),
                exit = shrinkVertically(shrinkTowards = Alignment.CenterVertically)
            ) {
                LanguagePickerDialog(
                    state = rememberDownloadableLanguageDialogState(
                        state = state
                    ),
                    heading = {
                        Text(
                            text = stringResource(
                                id = when (showSelectLanguageDialog) {
                                    TranslationLanguageOption.ORIGINAL_LANGUAGE -> R.string.label_select_original_language
                                    else -> R.string.label_select_target_language
                                }
                            ),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    },
                    onDismissDialog = {
                        showSelectLanguageDialog = null
                        onStateChanged(
                            state.copy(
                                downloadingLanguages = persistentListOf(),
                                deletingLanguages = persistentListOf()
                            )
                        )
                    },
                    onLanguageChosen = {
                        when (showSelectLanguageDialog) {
                            TranslationLanguageOption.ORIGINAL_LANGUAGE -> {
                                onStateChanged(
                                    state.copy(
                                        originalLanguage = it,
                                        downloadingLanguages = persistentListOf(),
                                        deletingLanguages = persistentListOf()
                                    )
                                )

                            }

                            TranslationLanguageOption.TARGET_LANGUAGE -> {
                                onStateChanged(
                                    state.copy(
                                        targetLanguage = it,
                                        downloadingLanguages = persistentListOf(),
                                        deletingLanguages = persistentListOf()
                                    )
                                )
                            }

                            null -> {}
                        }
                        showSelectLanguageDialog = null
                    },
                    onLanguageDeleted = onLanguageDeleted,
                    onLanguageDownloaded = onLanguageDownloaded,
                )
            }
        }

    }

}

@Preview
@Composable
private fun PreviewTranslationScreen() {
    var state by remember { mutableStateOf(TranslationScreenState()) }

    MLKitSampleTheme {
        TranslationScreenContent(
            state = state,
            onStateChanged = {
                state = it
            }
        )
    }
}