package com.github.bkmbigo.mlkitsample.ui.screens.text.states

import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.EntityExtractionLanguage
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class EntityExtractionScreenState(
    val currentLanguage: EntityExtractionLanguage? = null,
    val loadingLanguage: Boolean = false,
    val records: PersistentList<EntityRecordState> = persistentListOf(),
    val downloadedLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
    val downloadingLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
    val errorLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
    val deletingLanguages: PersistentList<EntityExtractionLanguage> = persistentListOf(),
)
