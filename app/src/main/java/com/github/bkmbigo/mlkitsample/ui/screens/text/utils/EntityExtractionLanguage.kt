package com.github.bkmbigo.mlkitsample.ui.screens.text.utils

import com.google.mlkit.nl.entityextraction.EntityExtractorOptions

enum class EntityExtractionLanguage(
    val languageView: LanguageView,
    val entityExtractionOption: String
) {
    ARABIC(LanguageView.ARABIC, EntityExtractorOptions.ARABIC),
    PORTUGUESE(LanguageView.PORTUGUESE, EntityExtractorOptions.PORTUGUESE),
    ENGLISH(LanguageView.ENGLISH, EntityExtractorOptions.ENGLISH),
    DUTCH(LanguageView.DUTCH, EntityExtractorOptions.DUTCH),
    FRENCH(LanguageView.FRENCH, EntityExtractorOptions.FRENCH),
    GERMAN(LanguageView.GERMAN, EntityExtractorOptions.GERMAN),
    ITALIAN(LanguageView.ITALIAN, EntityExtractorOptions.ITALIAN),
    JAPANESE(LanguageView.JAPANESE, EntityExtractorOptions.JAPANESE),
    KOREAN(LanguageView.KOREAN, EntityExtractorOptions.KOREAN),
    POLISH(LanguageView.POLISH, EntityExtractorOptions.POLISH),
    RUSSIAN(LanguageView.RUSSIAN, EntityExtractorOptions.RUSSIAN),
    CHINESE(LanguageView.CHINESE, EntityExtractorOptions.CHINESE),
    SPANISH(LanguageView.SPANISH, EntityExtractorOptions.SPANISH),
    THAI(LanguageView.THAI, EntityExtractorOptions.THAI),
    TURKISH(LanguageView.TURKISH, EntityExtractorOptions.TURKISH);

    companion object {

        private val entityLanguageMap = values().associateBy { it.entityExtractionOption }

        fun getEntityExtractionLanguage(languageCode: String) = entityLanguageMap[languageCode]
    }
}