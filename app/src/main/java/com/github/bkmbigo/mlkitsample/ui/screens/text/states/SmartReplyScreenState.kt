package com.github.bkmbigo.mlkitsample.ui.screens.text.states

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

data class SmartReplyScreenState(
    val conversations: PersistentList<SmartReplyConversation> = persistentListOf(),
    val loading: Boolean = false,
    val currentParticipant: SmartReplyParticipant,
    val predictions: PersistentList<String> = persistentListOf()
)
