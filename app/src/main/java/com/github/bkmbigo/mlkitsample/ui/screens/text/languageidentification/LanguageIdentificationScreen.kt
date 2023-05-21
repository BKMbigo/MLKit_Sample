package com.github.bkmbigo.mlkitsample.ui.screens.text.languageidentification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.MainNavGraph
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.languageidentification.LanguageIdentificationRecord
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@MainNavGraph
@Destination
@Composable
fun LanguageIdentificationScreen(
    text: String? = null,
    navigator: DestinationsNavigator
) {
    val languageIdentifier = LanguageIdentification.getClient()

    var state by remember { mutableStateOf(LanguageIdentificationState()) }


    LanguageIdentificationContent(
        state = state,
        onIdentifyLanguage = { text ->
            val languageCode = languageIdentifier.identifyLanguage(text).await()
            state = state.copy(
                records = state.records.toMutableList().apply {
                    add(LanguageIdentificationRecordState(text, languageCode))
                }
            )
        },
        onNavigateBack = { navigator.navigateUp() },
        onCopyContent = {},
        onShareContent = {}

    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageIdentificationContent(
    state: LanguageIdentificationState,
    onIdentifyLanguage: suspend (String) -> Unit = {},
    onNavigateBack: () -> Unit = {},
    onCopyContent: (String) -> Unit = {},
    onShareContent: (String) -> Unit = {},
) {

    val coroutineScope = rememberCoroutineScope()

    var inputText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }

    val isInputTextBlank by remember {
        derivedStateOf { inputText.text.isBlank() }
    }

    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { /*NO OP*/ },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onNavigateBack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChevronLeft,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = stringResource(id = R.string.label_language_identification),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                state = scrollState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true)
                    .padding(vertical = 8.dp),
            ) {
                items(state.records) { record ->
                    LanguageIdentificationRecord(
                        state = record,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = 6.dp,
                                vertical = 8.dp
                            ),
                        onCopyContent = onCopyContent,
                        onShareContent = onShareContent
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    )
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
                            onIdentifyLanguage(inputText.text)
                            scrollState.animateScrollToItem(Int.MAX_VALUE)
                            inputText = TextFieldValue("")
                        }
                    },
                    enabled = !isInputTextBlank
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewLanguageIdentification() {
    MLKitSampleTheme {
        LanguageIdentificationContent(
            state = LanguageIdentificationState(),
        )
    }
}