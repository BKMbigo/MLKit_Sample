package com.github.bkmbigo.mlkitsample.ui.screens.text.states

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EntityExtractionScreenState(
    val currentLanguage: LanguageView? = null,
    val loadingLanguage: Boolean = false,
    val records: PersistentList<EntityRecordState> = persistentListOf(),
    val downloadedLanguages: PersistentList<LanguageView> = persistentListOf(),
    val downloadingLanguages: PersistentList<LanguageView> = persistentListOf(),
    val errorLanguages: PersistentList<LanguageView> = persistentListOf(),
    val deletingLanguages: PersistentList<LanguageView> = persistentListOf(),
)
