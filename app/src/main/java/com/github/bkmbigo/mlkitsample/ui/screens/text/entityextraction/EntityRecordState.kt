package com.github.bkmbigo.mlkitsample.ui.screens.text.entityextraction

import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.EntityExtractionLanguage
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import kotlinx.collections.immutable.PersistentList

data class EntityRecordState(
    val languageView: EntityExtractionLanguage,
    val loading: Boolean = true,
    val text: String,
    val entities: PersistentList<EntityAnnotation>
)