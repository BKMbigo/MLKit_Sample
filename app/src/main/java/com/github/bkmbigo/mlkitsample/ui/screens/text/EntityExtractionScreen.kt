package com.github.bkmbigo.mlkitsample.ui.screens.text

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.bkmbigo.mlkitsample.MainNavGraph
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.LanguagePickerDialog
import com.github.bkmbigo.mlkitsample.ui.components.dialogs.states.rememberDownloadableLanguageDialogState
import com.github.bkmbigo.mlkitsample.ui.components.entityextraction.EntityRecord
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityExtractionScreenState
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@MainNavGraph
@Destination
@Composable
fun EntityExtractionScreen(
    navigator: DestinationsNavigator
) {
    val viewModel: EntityExtractionViewModel = viewModel()

    val state by viewModel.state.collectAsState()


    EntityExtractionScreenContent(
        state = state,
        onNavigateUp = { navigator.navigateUp() },
        onExtractEntity = viewModel::extractEntities,
        onLanguageChange = viewModel::updateLanguage,
        onLanguageDownloaded = viewModel::downloadLanguage,
        onLanguageDeleted = viewModel::deleteLanguage
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntityExtractionScreenContent(
    state: EntityExtractionScreenState,
    onNavigateUp: () -> Unit = {},
    onExtractEntity: suspend (LanguageView, String) -> Unit = { _, _ -> },
    onLanguageChange: (LanguageView) -> Unit = {},
    onLanguageDownloaded: suspend (LanguageView) -> Unit = {},
    onLanguageDeleted: suspend (LanguageView) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    var showLanguagePickerDialog by remember { mutableStateOf(false) }

    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(
            TextFieldValue("")
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { /*NO OP*/ },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.label_entity_extraction),
                    fontSize = 24.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, true),
                    contentPadding = PaddingValues(vertical = 6.dp)
                ) {
                    items(state.records.toList()) { record ->
                        EntityRecord(
                            state = record,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 8.dp,
                                    vertical = 4.dp
                                )
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                        .clip(
                            RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                        )
                ) {
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.label_language_value),
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        TextButton(
                            onClick = {
                                showLanguagePickerDialog = true
                            }
                        ) {
                            Text(
                                text = stringResource(
                                    id = state.currentLanguage?.string
                                        ?: R.string.label_select_language
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = {
                                inputText = it
                            },
                            modifier = Modifier.weight(1f, true),
                            placeholder = {
                                Text(
                                    text = stringResource(id = R.string.placeholder_enter_text)
                                )
                            }
                        )
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    state.currentLanguage?.let {
                                        onExtractEntity(it, inputText.text)
                                    }
                                    inputText = TextFieldValue("")
                                }
                            },
                            enabled = state.currentLanguage != null && inputText.text.isNotBlank()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }

        if (showLanguagePickerDialog) {
            LanguagePickerDialog(
                state = rememberDownloadableLanguageDialogState(state = state),
                heading = {
                    Text(
                        text = stringResource(id = R.string.label_select_language),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                },
                onDismissDialog = { showLanguagePickerDialog = false },
                onLanguageChosen = {
                    onLanguageChange(it)
                    showLanguagePickerDialog = false
                },
                onLanguageDeleted = onLanguageDeleted,
                onLanguageDownloaded = onLanguageDownloaded
            )
        }
    }
}

@Preview
@Composable
private fun PreviewEntityExtractionScreen() {

    var state by remember {
        mutableStateOf(
            EntityExtractionScreenState(

            )
        )
    }

    MLKitSampleTheme {
        EntityExtractionScreenContent(
            state = state,
        )
    }
}