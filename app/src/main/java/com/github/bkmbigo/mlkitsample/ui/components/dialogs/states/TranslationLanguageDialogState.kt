package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.bkmbigo.mlkitsample.ui.screens.text.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationLanguageOption
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationScreenState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class TranslationLanguageDialogState(
    val languageOption: TranslationLanguageOption,
    val languages: PersistentList<TranslationLanguageView>,
)

@Composable
fun rememberTranslationLanguageDialogState(
    languageOption: TranslationLanguageOption,
    downloadedLanguages: PersistentList<LanguageView> = persistentListOf(),
    downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
    errorLanguages: PersistentList<LanguageView> = persistentListOf(),
    deletingLanguages: PersistentList<LanguageView> = persistentListOf()
): TranslationLanguageDialogState = remember {
    TranslationLanguageDialogState(
        languageOption,
        TranslationLanguageView.generateTranslationLanguageViews(
            downloadedLanguages = downloadedLanguages,
            downloadingLanguages = downloadingLanguages,
            errorLanguages = errorLanguages,
            deletingLanguages = deletingLanguages
        ).toPersistentList()
    )
}

@Composable
fun rememberTranslationLanguageDialogState(
    languageOption: TranslationLanguageOption,
    state: TranslationScreenState
): TranslationLanguageDialogState = remember(state) {
    TranslationLanguageDialogState(
        languageOption,
        TranslationLanguageView.generateTranslationLanguageViews(
            downloadedLanguages = state.downloadedLanguages,
            downloadingLanguages = state.downloadingLanguages,
            errorLanguages = state.errorLanguages,
            deletingLanguages = state.deletingLanguages
        ).toPersistentList()
    )
}
