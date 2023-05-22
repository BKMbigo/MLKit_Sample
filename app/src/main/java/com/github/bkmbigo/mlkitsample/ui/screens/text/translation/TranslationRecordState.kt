package com.github.bkmbigo.mlkitsample.ui.screens.text.translation

import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView

data class TranslationRecordState (
    val originalText: String,
    val originalLanguage: TranslationLanguageView,
    val translations: List<TranslatedText>
)
