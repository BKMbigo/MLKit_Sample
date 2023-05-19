package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import com.github.bkmbigo.mlkitsample.ui.screens.text.LanguageView
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class TranslationLanguageViewTest {

    @Test
    fun `generateTranslationLanguageViews test`() {
        assertIterableEquals(
            listOf(
                TranslationLanguageView(
                    LanguageView.ENGLISH,
                    TranslationLanguageState.DOWNLOADED
                ),
                TranslationLanguageView(
                    LanguageView.SWAHILI,
                    TranslationLanguageState.DOWNLOADED
                ),
                TranslationLanguageView(
                    LanguageView.FRENCH,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.SPANISH,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.GERMAN,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.DUTCH,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.PORTUGUESE,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.CHINESE,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.JAPANESE,
                    TranslationLanguageState.AVAILABLE
                ),
                TranslationLanguageView(
                    LanguageView.HINDI,
                    TranslationLanguageState.AVAILABLE
                ),
            ),
            TranslationLanguageView.generateTranslationLanguageViews(
                downloadedLanguages = persistentListOf(
                    LanguageView.ENGLISH,
                    LanguageView.SWAHILI,
                )
            )
        )
    }
}