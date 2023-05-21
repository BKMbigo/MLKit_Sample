package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityExtractionScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.EntityExtractionLanguage
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class DownloadableLanguageDialogState<T>(
    val languages: PersistentList<DownloadableLanguageView<T>>,
)

@Composable
fun rememberDownloadableLanguageDialogState(
    downloadedLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    downloadingLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    errorLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    deletingLanguages: PersistentList<TranslationLanguageView> = persistentListOf()
): DownloadableLanguageDialogState<TranslationLanguageView> = remember {
    DownloadableLanguageDialogState(
        DownloadableLanguageView.generateTranslationDownloadableLanguageViews(
            downloadedLanguages = downloadedLanguages,
            downloadingLanguages = downloadingLanguages,
            errorLanguages = errorLanguages,
            deletingLanguages = deletingLanguages
        ).toPersistentList()
    )
}

@Composable
fun rememberDownloadableLanguageDialogState(
    state: TranslationScreenState
): DownloadableLanguageDialogState<TranslationLanguageView> = remember(state) {
    DownloadableLanguageDialogState(
        DownloadableLanguageView.generateTranslationDownloadableLanguageViews(
            downloadedLanguages = state.downloadedLanguages,
            downloadingLanguages = state.downloadingLanguages,
            errorLanguages = state.errorLanguages,
            deletingLanguages = state.deletingLanguages
        ).toPersistentList()
    )
}

@Composable
fun rememberDownloadableLanguageDialogState(
    state: EntityExtractionScreenState
): DownloadableLanguageDialogState<EntityExtractionLanguage> = remember(state) {
    DownloadableLanguageDialogState(
        DownloadableLanguageView.generateEntityExtractionDownloadableLanguageViews(
            downloadedLanguages = state.downloadedLanguages,
            downloadingLanguages = state.downloadingLanguages,
            errorLanguages = state.errorLanguages,
            deletingLanguages = state.deletingLanguages
        ).toPersistentList()
    )
}
