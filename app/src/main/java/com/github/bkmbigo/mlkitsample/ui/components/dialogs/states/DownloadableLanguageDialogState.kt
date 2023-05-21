package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityExtractionScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationLanguageOption
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.TranslationScreenState
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

data class DownloadableLanguageDialogState(
    val languages: PersistentList<DownloadableLanguageView>,
)

@Composable
fun rememberDownloadableLanguageDialogState(
    downloadedLanguages: PersistentList<LanguageView> = persistentListOf(),
    downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
    errorLanguages: PersistentList<LanguageView> = persistentListOf(),
    deletingLanguages: PersistentList<LanguageView> = persistentListOf()
): DownloadableLanguageDialogState = remember {
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
): DownloadableLanguageDialogState = remember(state) {
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
): DownloadableLanguageDialogState = remember(state) {
    DownloadableLanguageDialogState(
        DownloadableLanguageView.generateEntityExtractionDownloadableLanguageViews(
            downloadedLanguages = state.downloadedLanguages,
            downloadingLanguages = state.downloadingLanguages,
            errorLanguages = state.errorLanguages,
            deletingLanguages = state.deletingLanguages
        ).toPersistentList()
    )
}
