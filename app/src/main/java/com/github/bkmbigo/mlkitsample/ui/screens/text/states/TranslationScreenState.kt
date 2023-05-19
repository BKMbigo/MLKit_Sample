package com.github.bkmbigo.mlkitsample.ui.screens.text.states

import androidx.compose.ui.text.input.TextFieldValue
import com.github.bkmbigo.mlkitsample.ui.screens.text.LanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class TranslationScreenState(
    val originalLanguage: LanguageView? = null,
    val targetLanguage: LanguageView? = null,
    val originalText: TextFieldValue = TextFieldValue(""),
    val targetText: TextFieldValue = TextFieldValue(""),
    val downloadedLanguages: PersistentList<LanguageView> = persistentListOf(),
    val downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
    val errorLanguages: PersistentList<LanguageView> = persistentListOf(),
    val deletingLanguages: PersistentList<LanguageView> = persistentListOf()
)