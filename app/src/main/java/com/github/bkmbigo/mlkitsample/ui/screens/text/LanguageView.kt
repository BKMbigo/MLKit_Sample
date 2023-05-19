package com.github.bkmbigo.mlkitsample.ui.screens.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import com.github.bkmbigo.mlkitsample.R
import com.google.mlkit.nl.translate.TranslateLanguage

enum class LanguageView(
    val string: Int,
    val languageCode: String
) {
    ENGLISH(R.string.language_english, "en"),
    SWAHILI(R.string.language_swahili, "sw"),
    FRENCH(R.string.language_french, "fr"),
    SPANISH(R.string.language_spanish, "es"),
    GERMAN(R.string.language_german, "de"),
    DUTCH(R.string.language_dutch, "nl"),
    PORTUGUESE(R.string.language_portuguese, "pt"),
    CHINESE(R.string.language_chinese, "zh"),
    JAPANESE(R.string.language_japanese, "ja"),
    HINDI(R.string.language_hindi, "hi");

    fun getTranslateLanguage() = getTranslateLanguage(this)

    companion object {
        @Stable
        @Composable
        fun getLanguageName(languageCode: String) =
            when (languageCode) {
                "und" -> stringResource(id = R.string.language_unidentified)
                "en" -> stringResource(id = ENGLISH.string)
                "fr" -> stringResource(id = FRENCH.string)
                "es" -> stringResource(id = SPANISH.string)
                "de" -> stringResource(id = GERMAN.string)
                "nl" -> stringResource(id = DUTCH.string)
                "sw" -> stringResource(id = SWAHILI.string)
                "pt" -> stringResource(id = PORTUGUESE.string)
                "zh" -> stringResource(id = CHINESE.string)
                "zh-Latn" -> stringResource(id = CHINESE.string)
                "ja" -> stringResource(id = JAPANESE.string)
                "ja-Latn" -> stringResource(id = JAPANESE.string)
                "hi" -> stringResource(id = HINDI.string)
                else -> stringResource(id = R.string.language_new_language, languageCode)
            }


        @Stable
        fun getTranslateLanguage(languageView: LanguageView) =
            when (languageView) {
                ENGLISH -> TranslateLanguage.ENGLISH
                SWAHILI -> TranslateLanguage.SWAHILI
                FRENCH -> TranslateLanguage.FRENCH
                SPANISH -> TranslateLanguage.SPANISH
                GERMAN -> TranslateLanguage.GERMAN
                DUTCH -> TranslateLanguage.DUTCH
                PORTUGUESE -> TranslateLanguage.PORTUGUESE
                CHINESE -> TranslateLanguage.CHINESE
                JAPANESE -> TranslateLanguage.JAPANESE
                HINDI -> TranslateLanguage.HINDI
            }

        /**
         *
         * @throws IllegalArgumentException when [languageCode] does not belong to any specified [LanguageView]
         */
        // TODO: Automate using stored [languageCodes]
        @Stable
        fun getLanguageView(languageCode: String) =
            when(languageCode) {
                "en" -> ENGLISH
                "sw" -> SWAHILI
                "fr" -> FRENCH
                "es" -> SPANISH
                "nl" -> DUTCH
                "de" -> GERMAN
                "pt" -> PORTUGUESE
                "zh" -> CHINESE
                "ja" -> JAPANESE
                "hi" -> HINDI
                else -> throw IllegalArgumentException("")
            }

    }
}