package com.github.bkmbigo.mlkitsample.ui.screens.text.states

import androidx.compose.runtime.Composable
import com.google.mlkit.nl.entityextraction.Entity
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import kotlinx.collections.immutable.PersistentList

data class EntityRecordState(
    val languageView: LanguageView,
    val loading: Boolean = true,
    val text: String,
    val entities: PersistentList<EntityAnnotation>
)