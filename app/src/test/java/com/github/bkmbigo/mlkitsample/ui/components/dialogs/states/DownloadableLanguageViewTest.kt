package com.github.bkmbigo.mlkitsample.ui.components.dialogs.states

import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import kotlinx.collections.immutable.persistentListOf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test


class DownloadableLanguageViewTest {

    @Test
    fun `generateTranslationLanguageViews test`() {
        assertIterableEquals(
            listOf(
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.ENGLISH,
                    DownloadableLanguageState.DOWNLOADED
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.SWAHILI,
                    DownloadableLanguageState.DOWNLOADED
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.FRENCH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.SPANISH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.GERMAN,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.DUTCH,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.PORTUGUESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.CHINESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.JAPANESE,
                    DownloadableLanguageState.AVAILABLE
                ),
                DownloadableLanguageView<Any?>(
                    null,
                    LanguageView.HINDI,
                    DownloadableLanguageState.AVAILABLE
                ),
            ),
            DownloadableLanguageView.generateTranslationDownloadableLanguageViews(
                downloadedLanguages = persistentListOf(
                    TranslationLanguageView.ENGLISH,
                    TranslationLanguageView.SWAHILI,
                )
            )
        )
    }
}