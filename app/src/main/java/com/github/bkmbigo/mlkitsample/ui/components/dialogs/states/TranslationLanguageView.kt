package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import androidx.compose.runtime.Stable
import com.github.bkmbigo.mlkitsample.ui.screens.text.LanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class TranslationLanguageView(
    val languageView: LanguageView,
    val translationLanguageState: TranslationLanguageState = TranslationLanguageState.AVAILABLE
) {

    companion object {

        /**
         * Finds the appropriate [TranslationLanguageState] for all available [LanguageView]
         * @param downloadedLanguages List of downloadedLanguages
         * @param downloadingLanguages List of languages being currently downloaded
         * @param errorLanguages List of languages that have encountered an error while downloading or deleting
         * @param deletingLanguages List of languages currently being deleted
         * @return List<[TranslationLanguageView]>
         */
        @Stable
        fun generateTranslationLanguageViews(
            downloadedLanguages: PersistentList<LanguageView>,
            downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
            errorLanguages: PersistentList<LanguageView> = persistentListOf(),
            deletingLanguages: PersistentList<LanguageView> = persistentListOf()
        ): List<TranslationLanguageView> = LanguageView.values().toList().map { language ->
            TranslationLanguageView(
                language,
                if (errorLanguages.contains(language)) {
                    TranslationLanguageState.ERROR
                } else if (deletingLanguages.contains(language)) {
                    TranslationLanguageState.DELETING
                } else if (downloadingLanguages.contains(language)) {
                    TranslationLanguageState.DOWNLOADING
                } else if (downloadedLanguages.contains(language)) {
                    TranslationLanguageState.DOWNLOADED
                } else {
                    TranslationLanguageState.AVAILABLE
                }
            )
        }
    }
}
