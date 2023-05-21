package com.github.bkmbigo.mlkitsample.ui.screens.text

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityExtractionScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityRecordState
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import com.google.mlkit.common.MlKitException
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.entityextraction.EntityExtraction
import com.google.mlkit.nl.entityextraction.EntityExtractionParams
import com.google.mlkit.nl.entityextraction.EntityExtractionRemoteModel
import com.google.mlkit.nl.entityextraction.EntityExtractor
import com.google.mlkit.nl.entityextraction.EntityExtractorOptions
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EntityExtractionViewModel: ViewModel() {

    private val _state = MutableStateFlow(EntityExtractionScreenState())
    val state = _state.asStateFlow()

    private var currentEntityExtractor: EntityExtractor? =  null

    private val modelManager = RemoteModelManager.getInstance()
    private val downloadConditions = DownloadConditions.Builder().requireWifi().build()

    init {
        viewModelScope.launch {
            try {
                val downloadedLanguages = modelManager
                    .getDownloadedModels(EntityExtractionRemoteModel::class.java)
                    .await()
                    .mapNotNull {
                        try {
                            LanguageView.getEntityLanguageView(it)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    .toPersistentList()

                _state.value = state.value.copy(downloadedLanguages = downloadedLanguages)

            } catch (_: Exception) { }
        }
    }
    fun downloadLanguage(languageView: LanguageView) {
        viewModelScope.launch {
            val model = EntityExtractionRemoteModel
                .Builder(languageView.getEntityExtractionLanguage())
                .build()

            _state.value = state.value.copy(
                downloadingLanguages = state.value.downloadingLanguages.add(languageView),
                errorLanguages = state.value.errorLanguages.remove(languageView)
            )

            try {
                modelManager.download(model, downloadConditions).await()

                val downloadedLanguages = modelManager
                    .getDownloadedModels(EntityExtractionRemoteModel::class.java)
                    .await()
                    .mapNotNull {
                        try {
                            LanguageView.getEntityLanguageView(it)
                        } catch(e: Exception) { null }
                    }
                    .toPersistentList()

                _state.value = state.value.copy(
                    downloadedLanguages = downloadedLanguages,
                    downloadingLanguages = state.value.downloadingLanguages.remove(languageView)
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    downloadingLanguages = state.value.downloadingLanguages.remove(languageView),
                    errorLanguages = state.value.errorLanguages.add(languageView)
                )
            }
        }
    }

    fun deleteLanguage(languageView: LanguageView) {
        viewModelScope.launch {
            val model = EntityExtractionRemoteModel.Builder(languageView.getEntityExtractionLanguage()).build()
            _state.value = state.value.copy(
                deletingLanguages = state.value.deletingLanguages.add(languageView),
                errorLanguages = state.value.errorLanguages.remove(languageView)
            )
            try {
                modelManager.deleteDownloadedModel(model).await()
                val downloadedLanguages = modelManager
                    .getDownloadedModels(EntityExtractionRemoteModel::class.java)
                    .await()
                    .mapNotNull {
                        try {
                            LanguageView.getEntityLanguageView(it)
                        } catch(e: Exception) { null }
                    }
                    .toPersistentList()

                _state.value = state.value.copy(
                    downloadedLanguages = downloadedLanguages.toPersistentList(),
                    deletingLanguages = state.value.deletingLanguages.remove(languageView)
                )
            } catch (e: MlKitException) {
                _state.value = state.value.copy(
                    deletingLanguages = state.value.deletingLanguages.remove(languageView),
                    errorLanguages = state.value.errorLanguages.add(languageView)
                )
            }
        }
    }

    fun updateLanguage(languageView: LanguageView) {
        currentEntityExtractor?.let {
            it.close()
        }

        currentEntityExtractor = null

        val newEntityExtractor = EntityExtraction.getClient(
            EntityExtractorOptions
                .Builder(LanguageView.getEntityExtractionLanguage(languageView))
                .build()
        )

        viewModelScope.launch {
            if (!newEntityExtractor.isModelDownloaded().await()) {
                // Language is not downloaded
                _state.value = state.value.copy(
                    currentLanguage = null,
                    loadingLanguage = false
                )
            } else {
                currentEntityExtractor = newEntityExtractor
                _state.value = state.value.copy(
                    currentLanguage = languageView,
                    loadingLanguage = false
                )
            }
        }
    }

    fun extractEntities(languageView: LanguageView, text: String) {
        viewModelScope.launch {
            currentEntityExtractor?.let { entityExtractor ->

                val params = EntityExtractionParams
                    .Builder(text)
                    .build()

                val results = entityExtractor.annotate(params).await()

                _state.value = state.value.copy(
                    records = state.value.records.add(
                        EntityRecordState(
                            languageView,
                            false,
                            text,
                            results.toPersistentList()
                        )
                    )
                )
            }
        }
    }

}