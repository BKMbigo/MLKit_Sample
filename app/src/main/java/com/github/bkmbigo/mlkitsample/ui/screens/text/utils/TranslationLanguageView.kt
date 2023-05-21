package com.github.bkmbigo.mlkitsample.ui.screens.text.utils

import com.google.mlkit.nl.translate.TranslateLanguage

enum class TranslationLanguageView(
    val languageView: LanguageView,
    val translationLanguage: String
) {
    AFRIKAANS(LanguageView.AFRIKAANS, TranslateLanguage.AFRIKAANS),
    ARABIC(LanguageView.ARABIC, TranslateLanguage.ARABIC),
    BELARUSIAN(LanguageView.BELARUSIAN, TranslateLanguage.BELARUSIAN),
    BULGARIAN(LanguageView.BULGARIAN, TranslateLanguage.BULGARIAN),
    BENGALI(LanguageView.BENGALI, TranslateLanguage.BENGALI),
    CATALAN(LanguageView.CATALAN, TranslateLanguage.CATALAN),
    CZECH(LanguageView.CZECH, TranslateLanguage.CZECH),
    WELSH(LanguageView.WELSH, TranslateLanguage.WELSH),
    DANISH(LanguageView.DANISH, TranslateLanguage.DANISH),
    GERMAN(LanguageView.GERMAN, TranslateLanguage.GERMAN),
    GREEK(LanguageView.GREEK, TranslateLanguage.GREEK),
    ENGLISH(LanguageView.ENGLISH, TranslateLanguage.ENGLISH),
    ESPERANTO(LanguageView.ESPERANTO, TranslateLanguage.ESPERANTO),
    SPANISH(LanguageView.SPANISH, TranslateLanguage.SPANISH),
    ESTONIAN(LanguageView.ESTONIAN, TranslateLanguage.ESTONIAN),
    PERSIAN(LanguageView.PERSIAN, TranslateLanguage.PERSIAN),
    FINNISH(LanguageView.FINNISH, TranslateLanguage.FINNISH),
    FRENCH(LanguageView.FRENCH, TranslateLanguage.FRENCH),
    IRISH(LanguageView.IRISH, TranslateLanguage.IRISH),
    GALICIAN(LanguageView.GALICIAN, TranslateLanguage.GALICIAN),
    GUJARATI(LanguageView.GUJARATI, TranslateLanguage.GUJARATI),
    HEBREW(LanguageView.HEBREW, TranslateLanguage.HEBREW),
    HINDI(LanguageView.HINDI, TranslateLanguage.HINDI),
    CROATIAN(LanguageView.CROATIAN, TranslateLanguage.CROATIAN),
    HAITIAN(LanguageView.HAITIAN, TranslateLanguage.HAITIAN_CREOLE),
    HUNGARIAN(LanguageView.HUNGARIAN, TranslateLanguage.HUNGARIAN),
    INDONESIAN(LanguageView.INDONESIAN, TranslateLanguage.INDONESIAN),
    ICELANDIC(LanguageView.ICELANDIC, TranslateLanguage.ICELANDIC),
    ITALIAN(LanguageView.ITALIAN, TranslateLanguage.ITALIAN),
    JAPANESE(LanguageView.JAPANESE, TranslateLanguage.JAPANESE),
    GEORGIAN(LanguageView.GEORGIAN, TranslateLanguage.GEORGIAN),
    KANNADA(LanguageView.KANNADA, TranslateLanguage.KANNADA),
    KOREAN(LanguageView.KOREAN, TranslateLanguage.KOREAN),
    LITHUANIAN(LanguageView.LITHUANIAN, TranslateLanguage.LITHUANIAN),
    LATVIAN(LanguageView.LATVIAN, TranslateLanguage.LATVIAN),
    MACEDONIAN(LanguageView.MACEDONAIN, TranslateLanguage.MACEDONIAN),
    MARATHI(LanguageView.MARATHI, TranslateLanguage.MARATHI),
    MALAY(LanguageView.MALAY, TranslateLanguage.MALAY),
    MALTESE(LanguageView.MALTESE, TranslateLanguage.MALTESE),
    DUTCH(LanguageView.DUTCH, TranslateLanguage.DUTCH),
    NORWEGIAN(LanguageView.NORWEGIAN, TranslateLanguage.NORWEGIAN),
    POLISH(LanguageView.POLISH, TranslateLanguage.POLISH),
    PORTUGUESE(LanguageView.PORTUGUESE, TranslateLanguage.PORTUGUESE),
    ROMANIAN(LanguageView.ROMANIAN, TranslateLanguage.ROMANIAN),
    RUSSIAN(LanguageView.RUSSIAN, TranslateLanguage.RUSSIAN),
    SLOVAK(LanguageView.SLOVAK, TranslateLanguage.SLOVAK),
    SLOVENIAN(LanguageView.SLOVENIAN, TranslateLanguage.SLOVENIAN),
    ALBANIAN(LanguageView.ALBANIAN, TranslateLanguage.ALBANIAN),
    SWEDISH(LanguageView.SWEDISH, TranslateLanguage.SWEDISH),
    SWAHILI(LanguageView.SWAHILI, TranslateLanguage.SWAHILI),
    TAMIL(LanguageView.TAMIL, TranslateLanguage.TAMIL),
    TELUGU(LanguageView.TELUGU, TranslateLanguage.TELUGU),
    THAI(LanguageView.THAI, TranslateLanguage.THAI),
    TURKISH(LanguageView.TURKISH, TranslateLanguage.TURKISH),
    UKRAINIAN(LanguageView.UKRAINIAN, TranslateLanguage.UKRAINIAN),
    URDU(LanguageView.URDU, TranslateLanguage.URDU),
    VIETNAMESE(LanguageView.VIETMANESE, TranslateLanguage.VIETNAMESE),
    CHINESE(LanguageView.CHINESE, TranslateLanguage.CHINESE);

    companion object {

        private val translationLanguageMap = values().associateBy { it.translationLanguage }

        fun getTranslationLanguageView(languageCode: String) = translationLanguageMap[languageCode]
    }
}