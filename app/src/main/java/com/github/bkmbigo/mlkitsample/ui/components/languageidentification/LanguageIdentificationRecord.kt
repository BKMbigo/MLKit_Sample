package com.github.bkmbigo.mlkitsample.ui.components.languageidentification

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.ui.screens.text.languageidentification.LanguageIdentificationRecordState
import com.github.bkmbigo.mlkitsample.ui.screens.text.utils.LanguageView
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme

@Composable
fun LanguageIdentificationRecord(
    state: LanguageIdentificationRecordState,
    modifier: Modifier = Modifier,
    onCopyContent: (String) -> Unit = {},
    onShareContent: (String) -> Unit = {}
) {

    Card(
        modifier = modifier
    ) {

        Column(
            modifier = Modifier.padding(vertical = 4.dp)
        ) {
            Spacer(modifier = Modifier.height(2.dp))

            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    text = LanguageView.getLanguageName(state.languageCode),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = state.text,
            textAlign = TextAlign.Center,
            fontSize = 19.sp,
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 6.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = { onCopyContent(state.text) }
            ) {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null
                )
            }

            IconButton(
                onClick = { onShareContent(state.text) }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(2.dp))

    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun PreviewLanguageIdentificationRecord() {
    MLKitSampleTheme {
        Scaffold {
            LanguageIdentificationRecord(
                state = LanguageIdentificationRecordState(
                    text = "Hujambo, Ndugu yangu",
                    languageCode = "sw",

                    ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            )
        }
    }
}