package com.github.bkmbigo.mlkitsample.ui.screens.text.smartreply

import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.SmartReplyParticipant
import com.google.mlkit.nl.smartreply.TextMessage

data class SmartReplyConversation(
    val participant: SmartReplyParticipant,
    val text: String,
    val time: Long
) {
    fun toTextMessage() = when(participant){
        SmartReplyParticipant.LOCAL_USER -> TextMessage.createForLocalUser(
            text,
            time
        )
        SmartReplyParticipant.REMOTE_USER -> TextMessage.createForRemoteUser(
            text,
            time,
            "User 1"
        )
    }
}