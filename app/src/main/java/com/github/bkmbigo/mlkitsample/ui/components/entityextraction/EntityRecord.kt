package com.github.bkmbigo.mlkitsample.ui.components.entityextraction

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.PermPhoneMsg
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Web
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.EntityRecordState
import com.github.bkmbigo.mlkitsample.ui.screens.text.states.LanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.google.android.gms.internal.mlkit_entity_extraction.zzahy
import com.google.mlkit.nl.entityextraction.DateTimeEntity
import com.google.mlkit.nl.entityextraction.Entity
import com.google.mlkit.nl.entityextraction.EntityAnnotation
import com.google.mlkit.nl.entityextraction.FlightNumberEntity
import com.google.mlkit.nl.entityextraction.IbanEntity
import com.google.mlkit.nl.entityextraction.IsbnEntity
import com.google.mlkit.nl.entityextraction.MoneyEntity
import com.google.mlkit.nl.entityextraction.PaymentCardEntity
import com.google.mlkit.nl.entityextraction.TrackingNumberEntity
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EntityRecord(
    state: EntityRecordState,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit = {},
    onOpenCalendar: (Long) -> Unit = {},
    onDailNumber: (String) -> Unit = {},
    onSendMessage: (String) -> Unit = {},
    onAddToContacts: (String) -> Unit = {}
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = state.languageView.string),
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 8.dp)
            )

            IconButton(
                onClick = onDelete,
            ) {
                Icon(
                    imageVector = Icons.Default.DeleteForever,
                    contentDescription = null
                )
            }
        }

        Text(
            text = state.text,
            fontSize = 17.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
        )

        Spacer(modifier = Modifier.height(6.dp))

        state.entities.forEach { entityAnnotation ->
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    ),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    entityAnnotation.entities.forEach { entity ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .align(Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = getEntityIcon(entity),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(2.dp))

                            Text(
                                text = stringResource(id = getEntityText(entity)),
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            Text(
                                text = entityAnnotation.annotatedText,
                            )

                            FlowRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                when (entity) {
                                    is DateTimeEntity -> {
                                        if (entity.dateTimeGranularity > DateTimeEntity.GRANULARITY_WEEK) {
                                            ElevatedSuggestionChip(
                                                onClick = {
                                                    onOpenCalendar(entity.timestampMillis)
                                                },
                                                label = {
                                                    Text(
                                                        text = "Open Calendar"
                                                    )
                                                }
                                            )
                                        }
                                    }

                                    is PaymentCardEntity -> {}
                                    is MoneyEntity -> {}
                                    is IbanEntity -> {}
                                    is IsbnEntity -> {}
                                    is FlightNumberEntity -> {}
                                    is TrackingNumberEntity -> {}
                                    // To be removed when better support of the features arrives
                                    else -> {
                                        when(entity.type){
                                            Entity.TYPE_PHONE -> {
                                                ElevatedSuggestionChip(
                                                    onClick = { onDailNumber(entityAnnotation.annotatedText) },
                                                    icon = {
                                                           Icon(
                                                               imageVector = Icons.Default.Call,
                                                               contentDescription = null
                                                           )
                                                    },
                                                    label = {
                                                        Text(
                                                            text = "Dail Number"
                                                        )
                                                    }
                                                )

                                                Spacer(modifier = Modifier.width(8.dp))

                                                ElevatedSuggestionChip(
                                                    onClick = { onSendMessage(entityAnnotation.annotatedText) },
                                                    icon = {
                                                        Icon(
                                                            imageVector = Icons.Default.Message,
                                                            contentDescription = null
                                                        )
                                                    },
                                                    label = {
                                                        Text(
                                                            text = "Send Message"
                                                        )
                                                    }
                                                )

                                                Spacer(modifier = Modifier.width(8.dp))

                                                ElevatedSuggestionChip(
                                                    onClick = { onAddToContacts(entityAnnotation.annotatedText) },
                                                    icon = {
                                                        Icon(
                                                            imageVector = Icons.Default.PersonAdd,
                                                            contentDescription = null
                                                        )
                                                    },
                                                    label = {
                                                        Text(
                                                            text = "Add to Contacts"
                                                        )
                                                    }
                                                )
                                            }
                                            Entity.TYPE_EMAIL -> {

                                            }
                                            Entity.TYPE_URL -> {

                                            }
                                            else -> {}
                                        }
                                    }
                                }

                                Row {
                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ContentCopy,
                                            contentDescription = null,
                                        )
                                    }

                                    IconButton(
                                        onClick = {}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Share,
                                            contentDescription = null,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

private fun getEntityIcon(entity: Entity): ImageVector =
    when (entity.type) {
        Entity.TYPE_ADDRESS -> Icons.Default.Place
        Entity.TYPE_EMAIL -> Icons.Default.Email
        Entity.TYPE_IBAN, Entity.TYPE_ISBN -> Icons.Default.Book
        Entity.TYPE_MONEY -> Icons.Default.Money
        Entity.TYPE_DATE_TIME -> Icons.Default.DateRange
        Entity.TYPE_PAYMENT_CARD -> Icons.Default.CreditCard
        Entity.TYPE_PHONE -> Icons.Default.PermPhoneMsg
        Entity.TYPE_FLIGHT_NUMBER -> Icons.Default.Flight
        Entity.TYPE_URL -> Icons.Default.Web
        Entity.TYPE_TRACKING_NUMBER -> Icons.Default.ConfirmationNumber
        else -> Icons.Default.QuestionMark
    }

private fun getEntityText(entity: Entity): Int =
    when (entity.type) {
        Entity.TYPE_ADDRESS -> R.string.label_extraction_address
        Entity.TYPE_EMAIL -> R.string.label_extraction_email
        Entity.TYPE_IBAN, Entity.TYPE_ISBN -> R.string.label_extraction_book
        Entity.TYPE_MONEY -> R.string.label_extraction_money
        Entity.TYPE_DATE_TIME -> R.string.label_extraction_date_time
        Entity.TYPE_PAYMENT_CARD -> R.string.label_extraction_payment_card
        Entity.TYPE_PHONE -> R.string.label_extraction_phone
        Entity.TYPE_FLIGHT_NUMBER -> R.string.label_extraction_flight_number
        Entity.TYPE_URL -> R.string.label_extraction_url
        Entity.TYPE_TRACKING_NUMBER -> R.string.label_extraction_tracking_number
        else -> R.string.label_unknown
    }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewEntityRecord() {
    val state = EntityRecordState(
        languageView = LanguageView.ENGLISH,
        loading = false,
        text = "Meet me at 430, WestSide Nairobi on Wednesday at 6pm. Call me on 0711554545 for clarification.",
        entities = persistentListOf(
            EntityAnnotation(
                "430, WestSide Nairobi",
                0,
                21,
                zzahy.zzp(
                    arrayOf(
                        Entity(Entity.TYPE_ADDRESS)
                    )
                )
            ),
            EntityAnnotation(
                "Wednesday at 6pm",
                0,
                16,
                zzahy.zzp(
                    arrayOf(
                        DateTimeEntity(
                            Clock.System.now().toEpochMilliseconds(),
                            DateTimeEntity.GRANULARITY_HOUR
                        )
                    )
                )
            ),
            EntityAnnotation(
                "0711554545",
                0,
                10,
                zzahy.zzp(
                    arrayOf(
                        Entity(Entity.TYPE_PHONE)
                    )
                )
            )
        )
    )

    MLKitSampleTheme {
        Scaffold {
            EntityRecord(
                state = state
            )
        }
    }
}