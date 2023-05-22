package com.github.bkmbigo.mlkitsample.ui.screens.text.translation

import androidx.compose.ui.text.input.TextFieldValue
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.TranslationLanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class TranslationScreenState(
    val records: List<TranslationRecordState> = emptyList(),
    val downloadedLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    val downloadingLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    val errorLanguages: PersistentList<TranslationLanguageView> = persistentListOf(),
    val deletingLanguages: PersistentList<TranslationLanguageView> = persistentListOf()
)