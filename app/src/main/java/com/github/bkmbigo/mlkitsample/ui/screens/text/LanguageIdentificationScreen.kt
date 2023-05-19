package com.github.bkmbigo.mlkitsample.ui.screens.text

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.MainNavGraph
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.components.TextInputTextField
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@MainNavGraph
@Destination
@Composable
fun LanguageIdentificationScreen(text: String? = null) {
    val languageIdentifier = LanguageIdentification.getClient()

    var languageIdentificationState by remember {
        mutableStateOf<LanguageIdentificationState>(LanguageIdentificationState.UnidentifiedLanguage)
    }

    LanguageIdentificationContent(
        state = languageIdentificationState,
        onIdentifyLanguage = { text ->
            val languageCode = languageIdentifier.identifyLanguage(text).await()
            languageIdentificationState =
                LanguageIdentificationState.IdentifiedLanguage(languageCode)
        },
        onNavigateBack = {},
        onCopyContent = {},
        onShareContent = {},

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 45.dp)
                    .padding(horizontal = 16.dp)
                    .shadow(
                        elevation = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = when (state) {
                        is LanguageIdentificationState.IdentifiedLanguage -> LanguageView.getLanguageName(
                            languageCode = state.languageCode
                        )
                        LanguageIdentificationState.UnidentifiedLanguage -> ""
                    },
                    fontSize = 17.sp,
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                verticalArrangement = Arrangement.Center
            ) {

                TextInputTextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    topActions = {
                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = stringResource(id = R.string.placeholder_enter_text),
                            fontWeight = FontWeight.Light,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .padding(start = 4.dp)
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f, true)
                                .height(ButtonDefaults.MinHeight)
                                .padding(vertical = 4.dp)
                        )

                        AnimatedVisibility(
                            visible = !isInputTextBlank,
                            enter = slideInHorizontally { it / 2 },
                            exit = slideOutHorizontally { it / 2 }
                        ) {
                            IconButton(
                                onClick = {
                                    inputText = TextFieldValue("")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(4.dp))
                    },
                    bottomActions = {
                        Box(
                            modifier = Modifier
                                .weight(1f, true)
                                .height(ButtonDefaults.MinHeight)
                        )

                        AnimatedVisibility(
                            visible = !isInputTextBlank,
                            enter = slideInHorizontally { it / 2 },
                            exit = slideOutHorizontally { it / 2 }
                        ) {

                            IconButton(
                                onClick = {
                                    onCopyContent(inputText.text)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = !isInputTextBlank,
                            enter = slideInHorizontally { it / 4 },
                            exit = slideOutHorizontally { it / 4 }
                        ) {
                            IconButton(
                                onClick = {
                                    onShareContent(inputText.text)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = null
                                )
                            }
                        }

                        AnimatedVisibility(
                            visible = !isInputTextBlank,
                            enter = slideInHorizontally { it / 8 },
                            exit = slideOutHorizontally { it / 8 }
                        ) {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        onIdentifyLanguage(inputText.text)
                                    }
                                }
                            ) {
                                Text(
                                    text = "Identify",
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier
                                .width(4.dp)
                        )
                    }
                )

            }
        }
    }

}

private sealed class LanguageIdentificationState {
    object UnidentifiedLanguage : LanguageIdentificationState()

    data class IdentifiedLanguage(
        val languageCode: String
    ) : LanguageIdentificationState()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewLanguageIdentification() {
    MLKitSampleTheme {
        LanguageIdentificationContent(
            state = LanguageIdentificationState.UnidentifiedLanguage,
        )
    }
}