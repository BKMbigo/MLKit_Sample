package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Stable
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class DownloadableLanguageView(
    val languageView: LanguageView,
    val downloadableLanguageState: DownloadableLanguageState = DownloadableLanguageState.AVAILABLE
) {

    companion object {

        /**
         * Finds the appropriate [DownloadableLanguageState] for all available [LanguageView]
         * @param downloadedLanguages List of downloadedLanguages
         * @param downloadingLanguages List of languages being currently downloaded
         * @param errorLanguages List of languages that have encountered an error while downloading or deleting
         * @param deletingLanguages List of languages currently being deleted
         * @return List<[DownloadableLanguageView]>
         */
        @Stable
        fun generateTranslationDownloadableLanguageViews(
            downloadedLanguages: PersistentList<LanguageView>,
            downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
            errorLanguages: PersistentList<LanguageView> = persistentListOf(),
            deletingLanguages: PersistentList<LanguageView> = persistentListOf()
        ): List<DownloadableLanguageView> = LanguageView.values().toList().map { language ->
            DownloadableLanguageView(
                language,
                if (errorLanguages.contains(language)) {
                    DownloadableLanguageState.ERROR
                } else if (deletingLanguages.contains(language)) {
                    DownloadableLanguageState.DELETING
                } else if (downloadingLanguages.contains(language)) {
                    DownloadableLanguageState.DOWNLOADING
                } else if (downloadedLanguages.contains(language)) {
                    DownloadableLanguageState.DOWNLOADED
                } else {
                    DownloadableLanguageState.AVAILABLE
                }
            )
        }

        @Stable
        fun generateEntityExtractionDownloadableLanguageViews(
            downloadedLanguages: PersistentList<LanguageView>,
            downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
            errorLanguages: PersistentList<LanguageView> = persistentListOf(),
            deletingLanguages: PersistentList<LanguageView> = persistentListOf()
        ): List<DownloadableLanguageView> = LanguageView.getAvailableEntityExtractionLanguages().map { language ->
            DownloadableLanguageView(
                language,
                if (errorLanguages.contains(language)) {
                    DownloadableLanguageState.ERROR
                } else if (deletingLanguages.contains(language)) {
                    DownloadableLanguageState.DELETING
                } else if (downloadingLanguages.contains(language)) {
                    DownloadableLanguageState.DOWNLOADING
                } else if (downloadedLanguages.contains(language)) {
                    DownloadableLanguageState.DOWNLOADED
                } else {
                    DownloadableLanguageState.AVAILABLE
                }
            )
        }
    }
}
