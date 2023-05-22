package com.github.bkmbigo.mlkitsample.ui.screens.text.translation

import androidx.compose.animation.ExperimentalAnimationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.LanguagePickerDialog
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.rememberDownloadableLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.components.translation.TranslationRecord
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
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@MainNavGraph
@Destination
@Composable
fun TranslationScreen(
    navigator: DestinationsNavigator
) {

    val modelManager = remember { RemoteModelManager.getInstance() }
    val downloadConditions = remember { DownloadConditions.Builder().requireWifi().build() }

    var state by remember {
        mutableStateOf(TranslationScreenState())
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
                TranslatedText(targetLanguage, translated)
            } catch (exception: MlKitException) {
                null
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
    onTranslateText: suspend (TranslationLanguageView, TranslationLanguageView, String) -> TranslatedText?,
    onCopyContent: (String) -> Unit = {},
    onShareContent: (String) -> Unit = {},
    onLanguageDeleted: suspend (TranslationLanguageView) -> Unit = {},
    onLanguageDownloaded: suspend (TranslationLanguageView) -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()

    val dialogState = rememberDownloadableLanguageDialogState(state = state)
    var showSelectLanguageDialog by remember { mutableStateOf<TranslationLanguageOption?>(null) }

    var originalLanguage by remember { mutableStateOf<TranslationLanguageView?>(null) }
    var targetLanguage by remember { mutableStateOf<TranslationLanguageView?>(null) }
    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    val isSendButtonEnabled by remember {
        derivedStateOf {
            inputText.text.isNotBlank() && originalLanguage != null && targetLanguage != null
        }
    }

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


                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true)
                        .padding(horizontal = 2.dp),
                    contentPadding = PaddingValues(vertical = 4.dp),
                ) {
                    itemsIndexed(state.records) { index, record ->
                        TranslationRecord(
                            state = record,
                            dialogState = dialogState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            onCopyContent = onCopyContent,
                            onShareContent = onShareContent,
                            onTranslateText = { originalLang, targetLang, text ->
                                coroutineScope.launch {
                                    val translation =
                                        onTranslateText(originalLang, targetLang, text)
                                    onStateChanged(
                                        state.copy(records = state.records.toMutableList().apply {
                                            val rec = get(index)
                                            set(
                                                index,
                                                rec.copy(
                                                    translations = rec.translations.toMutableList()
                                                        .apply {
                                                            translation?.let { add(it) }
                                                        })
                                            )
                                        })
                                    )
                                }
                            },
                            onLanguageDownloaded = onLanguageDownloaded,
                            onLanguageDeleted = onLanguageDeleted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(
                            onClick = {
                                showSelectLanguageDialog =
                                    TranslationLanguageOption.ORIGINAL_LANGUAGE
                            }
                        ) {
                            Text(
                                text = stringResource(
                                    id = originalLanguage?.languageView?.string
                                        ?: R.string.label_select_language
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        IconButton(
                            onClick = {
                                val lang = originalLanguage
                                originalLanguage = targetLanguage
                                targetLanguage = lang
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.SwapHoriz,
                                contentDescription = null
                            )
                        }

                        Spacer(modifier = Modifier.width(4.dp))

                        TextButton(
                            onClick = {
                                showSelectLanguageDialog = TranslationLanguageOption.TARGET_LANGUAGE
                            }
                        ) {
                            Text(
                                text = stringResource(
                                    id = targetLanguage?.languageView?.string
                                        ?: R.string.label_select_language
                                )
                            )
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp)
                            .padding(bottom = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        OutlinedTextField(
                            value = inputText,
                            onValueChange = {
                                inputText = it
                            },
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.placeholder_enter_text)
                                )
                            },
                            modifier = Modifier.weight(1f, true)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    originalLanguage?.let { origLang ->
                                        targetLanguage?.let { targetLang ->
                                            val translation = onTranslateText(
                                                origLang,
                                                targetLang,
                                                inputText.text
                                            )
                                            onStateChanged(
                                                state.copy(
                                                    records = state.records.toMutableList().apply {
                                                        add(
                                                            TranslationRecordState(
                                                                originalText = inputText.text,
                                                                originalLanguage = origLang,
                                                                translations = translation?.let {
                                                                    listOf(it)
                                                                } ?: emptyList()
                                                            )
                                                        )
                                                    }
                                                )
                                            )
                                            inputText = TextFieldValue("")
                                        }
                                    }
                                }
                            },
                            enabled = isSendButtonEnabled
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }

    if(showSelectLanguageDialog != null) {
        LanguagePickerDialog(
            state = dialogState,
            heading = {
                Text(
                    text = when(showSelectLanguageDialog) {
                        TranslationLanguageOption.ORIGINAL_LANGUAGE -> stringResource(id = R.string.label_select_original_language)
                        TranslationLanguageOption.TARGET_LANGUAGE -> stringResource(id = R.string.label_select_target_language)
                        null -> ""
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            onDismissDialog = { showSelectLanguageDialog = null },
            onLanguageChosen = {
                when(showSelectLanguageDialog) {
                    TranslationLanguageOption.ORIGINAL_LANGUAGE -> { originalLanguage = it }
                    TranslationLanguageOption.TARGET_LANGUAGE -> { targetLanguage = it }
                    null -> {}
                }
                showSelectLanguageDialog = null
            },
            onLanguageDeleted = onLanguageDeleted,
            onLanguageDownloaded = onLanguageDownloaded,
            
        )
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
            },
            onTranslateText = { orig, target, text ->
                TranslatedText(target, "Translated Text")
            }
        )
    }
}