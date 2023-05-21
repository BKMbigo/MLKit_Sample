package com.github.bkmbigo.mlkitsample.ui.components.smartreply

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.bkmbigo.mlkitsample.ui.screens.text.smartreply.SmartReplyConversation
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.SmartReplyParticipant
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import kotlinx.datetime.Clock

@Composable
fun SmartReplyConversationBar(
    conversation: SmartReplyConversation,
    modifier: Modifier = Modifier,
    options: SmartReplyConversationBarOptions = rememberSmartReplyConversationBarOptions()
) {
    val density = LocalDensity.current

    val boxBackground by remember(conversation, options) {
        derivedStateOf {
            when (conversation.participant) {
                SmartReplyParticipant.LOCAL_USER -> options.localUserBoxBackground
                SmartReplyParticipant.REMOTE_USER -> options.remoteUserBoxBackground
            }
        }
    }

    BoxWithConstraints(
        modifier = modifier
    ) {
        val maxWidth = constraints.maxWidth
        Box(
            modifier = Modifier
                .align(
                    when (conversation.participant) {
                        SmartReplyParticipant.LOCAL_USER -> Alignment.TopEnd
                        SmartReplyParticipant.REMOTE_USER -> Alignment.TopStart
                    }
                )
                .padding(
                    vertical = 4.dp
                )
                .drawBehind {
                    drawRoundRect(
                        color = boxBackground,
                        topLeft = Offset(
                            x = when (conversation.participant) {
                                SmartReplyParticipant.LOCAL_USER -> 0f
                                SmartReplyParticipant.REMOTE_USER -> options.boxStartPadding.toPx()
                            },
                            y = 0f
                        ),
                        size = Size(
                            width = size.width - options.boxStartPadding.toPx(),
                            height = size.height,
                        ),
                        cornerRadius = CornerRadius(
                            x = options.barCornerRadius.toPx(),
                            y = options.barCornerRadius.toPx()
                        ),
                    )

                    drawPath(
                        path = Path()
                            .asAndroidPath()
                            .apply {
                                moveTo(
                                    when (conversation.participant) {
                                        SmartReplyParticipant.LOCAL_USER -> size.width
                                        SmartReplyParticipant.REMOTE_USER -> 0f
                                    },
                                    0f
                                )
                                lineTo(
                                    when (conversation.participant) {
                                        SmartReplyParticipant.LOCAL_USER -> size.width - options.boxStartPadding.toPx() * 2
                                        SmartReplyParticipant.REMOTE_USER -> options.boxStartPadding.toPx() * 2
                                    },
                                    0f
                                )
                                lineTo(
                                    when (conversation.participant) {
                                        SmartReplyParticipant.LOCAL_USER -> size.width - options.boxStartPadding.toPx() * 2
                                        SmartReplyParticipant.REMOTE_USER -> options.boxStartPadding.toPx() * 2
                                    },
                                    options.boxStartPadding.toPx() * 2
                                )
                            }
                            .asComposePath(),
                        color = boxBackground,
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(options.boxContentPadding)
                    .padding(
                        end = when (conversation.participant) {
                            SmartReplyParticipant.LOCAL_USER -> options.boxStartPadding
                            SmartReplyParticipant.REMOTE_USER -> 0.dp
                        },
                        start = when (conversation.participant) {
                            SmartReplyParticipant.LOCAL_USER -> 0.dp
                            SmartReplyParticipant.REMOTE_USER -> options.boxStartPadding
                        },
                    )
            ) {
                Text(
                    text = conversation.text,
                    modifier = Modifier
                        .widthIn(
                            max = with(density) { maxWidth.toDp() * options.barMaxWidth }
                        )
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "NewApi")
@Preview
@Composable
fun PreviewSmartReplyConversation() {
    MLKitSampleTheme {
        Scaffold {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center
            ) {
                SmartReplyConversationBar(
                    conversation = SmartReplyConversation(
                        participant = SmartReplyParticipant.LOCAL_USER,
                        text = "Hi",
                        time = Clock.System.now().epochSeconds
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                SmartReplyConversationBar(
                    conversation = SmartReplyConversation(
                        participant = SmartReplyParticipant.REMOTE_USER,
                        text = "Hi,\nHow are you?",
                        time = Clock.System.now().epochSeconds
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                SmartReplyConversationBar(
                    conversation = SmartReplyConversation(
                        participant = SmartReplyParticipant.LOCAL_USER,
                        text = "I'm fine\nLong time?",
                        time = Clock.System.now().epochSeconds
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                SmartReplyConversationBar(
                    conversation = SmartReplyConversation(
                        participant = SmartReplyParticipant.REMOTE_USER,
                        text = "Yeah,\nI've been busy",
                        time = Clock.System.now().epochSeconds
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
                SmartReplyConversationBar(
                    conversation = SmartReplyConversation(
                        participant = SmartReplyParticipant.LOCAL_USER,
                        text = "No Problem. In fact, I had a personal question. How did you approach the software issue you were facing?",
                        time = Clock.System.now().epochSeconds
                    ),
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}