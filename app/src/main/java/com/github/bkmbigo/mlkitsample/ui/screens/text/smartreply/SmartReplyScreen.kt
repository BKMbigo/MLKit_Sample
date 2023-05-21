package com.github.bkmbigo.mlkitsample.ui.screens.text.smartreply

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.github.bkmbigo.mlkitsample.ui.components.smartreply.SmartReplyConversationBar
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.SmartReplyParticipant
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.google.mlkit.nl.smartreply.SmartReply
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.datetime.Clock

@MainNavGraph
@Destination
@Composable
fun SmartReplyScreen(
    navigator: DestinationsNavigator
) {

    val smartReplyGenerator = remember { SmartReply.getClient() }

    var screenState by remember { mutableStateOf(SmartReplyScreenState(currentParticipant = SmartReplyParticipant.LOCAL_USER)) }

    SmartReplyScreenContent(
        state = screenState,
        onReply = { participant, text ->
            screenState = screenState.copy(
                loading = screenState.currentParticipant == SmartReplyParticipant.LOCAL_USER,
                conversations = screenState.conversations.add(
                    SmartReplyConversation(
                        participant = screenState.currentParticipant,
                        text = text,
                        time = Clock.System.now().epochSeconds
                    )
                ),
                predictions = persistentListOf()
            )

            if(screenState.conversations.lastOrNull()?.participant == SmartReplyParticipant.REMOTE_USER) {
                try {
                    val replies =
                        smartReplyGenerator.suggestReplies(screenState.conversations.map { it.toTextMessage() })
                            .await()

                    screenState = screenState.copy(
                        loading = false,
                        predictions = replies.suggestions.map { it.text }.toPersistentList()
                    )
                } catch (e: Exception) {
                    screenState = screenState.copy(loading = false)
                }
            } else {
                screenState = screenState.copy(loading = false)
            }
        },
        onChangeCurrentParticipant = {
            screenState = screenState.copy(currentParticipant = it)
        },
        onNavigateBack = {
                         navigator.navigateUp()
        },
        onClearChat = {
            screenState = screenState.copy(
                conversations = persistentListOf()
            )
        }
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun SmartReplyScreenContent(
    state: SmartReplyScreenState,
    onReply: suspend (SmartReplyParticipant, String) -> Unit,
    onChangeCurrentParticipant: (SmartReplyParticipant) -> Unit,
    onNavigateBack: () -> Unit = {},
    onClearChat: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    val currentParticipant by remember(state) { derivedStateOf { state.currentParticipant } }

    var isCurrentParticipantOpen by remember { mutableStateOf(false) }

    var entryText by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
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
                actions = {
                    if (state.conversations.isNotEmpty()) {
                        TextButton(
                            onClick = {
                                onClearChat()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.label_clear_chat))
                        }
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
                text = stringResource(id = R.string.label_smart_reply),
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, true),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(
                    items = state.conversations.toList(),
                    key = { it.time }
                ) { conversation ->
                    SmartReplyConversationBar(
                        conversation = conversation,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement()
                    )
                }
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.label_participant_value),
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Column {
                    if (isCurrentParticipantOpen) {

                        DropdownMenu(
                            expanded = isCurrentParticipantOpen,
                            onDismissRequest = { isCurrentParticipantOpen = false },
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Local"
                                    )
                                },
                                onClick = {
                                    onChangeCurrentParticipant(SmartReplyParticipant.LOCAL_USER)
                                    isCurrentParticipantOpen = false
                                }
                            )

                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = "Remote"
                                    )
                                },
                                onClick = {
                                    onChangeCurrentParticipant(SmartReplyParticipant.REMOTE_USER)
                                    isCurrentParticipantOpen = false
                                }
                            )
                        }

                    }

                    FilterChip(
                        selected = false,
                        onClick = { isCurrentParticipantOpen = true },
                        label = {
                            Text(
                                text = when (currentParticipant) {
                                    SmartReplyParticipant.LOCAL_USER -> "Local"
                                    SmartReplyParticipant.REMOTE_USER -> "Remote"
                                },
                            )
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = when (isCurrentParticipantOpen) {
                                    true -> Icons.Filled.ArrowDropDown
                                    false -> Icons.Filled.ArrowDropUp
                                },
                                contentDescription = null
                            )
                        }
                    )
                }
            }

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center
            ) {
                state.predictions.forEach { prediction ->
                    ElevatedSuggestionChip(
                        onClick = {
                            coroutineScope.launch {
                                onChangeCurrentParticipant(SmartReplyParticipant.LOCAL_USER)
                                onReply(currentParticipant, prediction)
                            }
                        },
                        label = {
                            Text(text = prediction)
                        },
                        elevation = SuggestionChipDefaults.elevatedSuggestionChipElevation(
                            elevation = 6.dp
                        ),
                        colors = SuggestionChipDefaults.elevatedSuggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = entryText,
                    onValueChange = {
                        entryText = it
                    },
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(horizontal = 4.dp),
                    placeholder = {
                        Text(
                            text = stringResource(id = R.string.label_reply)
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = when (currentParticipant) {
                            SmartReplyParticipant.LOCAL_USER -> MaterialTheme.colorScheme.primaryContainer
                            SmartReplyParticipant.REMOTE_USER -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        unfocusedContainerColor = when (currentParticipant) {
                            SmartReplyParticipant.LOCAL_USER -> MaterialTheme.colorScheme.primaryContainer
                            SmartReplyParticipant.REMOTE_USER -> MaterialTheme.colorScheme.secondaryContainer
                        },
                        focusedTextColor = when (currentParticipant) {
                            SmartReplyParticipant.LOCAL_USER -> MaterialTheme.colorScheme.onPrimaryContainer
                            SmartReplyParticipant.REMOTE_USER -> MaterialTheme.colorScheme.onSecondaryContainer
                        },
                        unfocusedTextColor = when (currentParticipant) {
                            SmartReplyParticipant.LOCAL_USER -> MaterialTheme.colorScheme.onPrimaryContainer
                            SmartReplyParticipant.REMOTE_USER -> MaterialTheme.colorScheme.onSecondaryContainer
                        },
                    )
                )

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            onReply(currentParticipant, entryText.text)
                            entryText = TextFieldValue("")
                        }
                    },
                    enabled = entryText.text.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Send,
                        contentDescription = null
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )
        }
    }
}

@SuppressLint("NewApi")
@Preview
@Composable
private fun PreviewSmartReplyScreen() {
    MLKitSampleTheme {
        var state by remember {
            mutableStateOf(
                SmartReplyScreenState(
                    conversations = persistentListOf(
                        SmartReplyConversation(
                            participant = SmartReplyParticipant.LOCAL_USER,
                            text = "Hi\nHow are you?",
                            time = Clock.System.now().epochSeconds + 1
                        ),
                        SmartReplyConversation(
                            participant = SmartReplyParticipant.REMOTE_USER,
                            text = "I'm fine.\n How have you been",
                            time = Clock.System.now().epochSeconds + 2
                        )
                    ),
                    loading = false,
                    currentParticipant = SmartReplyParticipant.LOCAL_USER,
                    predictions = persistentListOf(
                        "I've been fine",
                        "It's been hard",
                        "Fine"
                    )
                )
            )
        }

        SmartReplyScreenContent(
            state = state,
            onReply = { participant, text ->
                state = state.copy(
                    conversations = state.conversations.add(
                        SmartReplyConversation(participant, text, Clock.System.now().epochSeconds)
                    )
                )
            },
            onChangeCurrentParticipant = {
                state = state.copy(currentParticipant = it)
            }
        )
    }
}