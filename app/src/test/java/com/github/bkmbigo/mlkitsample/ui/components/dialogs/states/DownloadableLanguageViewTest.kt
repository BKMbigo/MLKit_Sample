package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class DownloadableLanguageViewTest {

    @Test
    fun `generateTranslationLanguageViews test`() {
        assertIterableEquals(
            listOf(
                DownloadableLanguageView(
                    LanguageView.ENGLISH,
                    DownloadableLanguageState.DOWNLOADED
                ),
                DownloadableLanguageView(
                    LanguageView.SWAHILI,
                    DownloadableLanguageState.DOWNLOADED
                ),
                DownloadableLanguageView(
                    LanguageView.FRENCH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.SPANISH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.GERMAN,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.DUTCH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.PORTUGUESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.CHINESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.JAPANESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView(
                    LanguageView.HINDI,
                    DownloadableLanguageState.AVAILABLE
                ),
            ),
            DownloadableLanguageView.generateDownloadableLanguageViews(
                downloadedLanguages = persistentListOf(
                    LanguageView.ENGLISH,
                    LanguageView.SWAHILI,
                )
            )
        )
    }
}