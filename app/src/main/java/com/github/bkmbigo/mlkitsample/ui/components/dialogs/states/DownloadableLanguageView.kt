package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Stable
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.EntityExtractionLanguage
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class DownloadableLanguageView <T>(
    val languageEntity: T,
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
            downloadedLanguages: PersistentList<TranslationLanguageView>,
            downloadingLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
            errorLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
            deletingLanguages: PersistentList<TranslationLanguageView> = persistentListOf()
        ): List<DownloadableLanguageView<TranslationLanguageView>> = TranslationLanguageView.values().map { language ->
            DownloadableLanguageView(
                language,
                language.languageView,
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
            downloadedLanguages: PersistentList<EntityExtractionLanguage>,
            downloadingLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
            errorLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
            deletingLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf()
        ): List<DownloadableLanguageView<EntityExtractionLanguage>> = EntityExtractionLanguage.values().map { language ->
            DownloadableLanguageView(
                languageEntity = language,
                languageView = language.languageView,
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
