package com.github.bkmbigo.mlkitsample.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.bkmbigo.mlkitsample.MainNavGraph
import com.github.bkmbigo.mlkitsample.R
import com.github.bkmbigo.mlkitsample.ui.screens.destinations.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@MainNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        HomeDestinationCard(
            label = {
                Text(
                    text = stringResource(id = R.string.label_language_identification),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            onClick = {
                navigator.navigate(LanguageIdentificationScreenDestination.route)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )

        HomeDestinationCard(
            label = {
                Text(
                    text = stringResource(id = R.string.label_translation),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            onClick = {
                navigator.navigate(TranslationScreenDestination.route)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )

        HomeDestinationCard(
            label = {
                Text(
                    text = stringResource(id = R.string.label_smart_reply),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            onClick = {
                navigator.navigate(SmartReplyScreenDestination.route)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )

        HomeDestinationCard(
            label = {
                Text(
                    text = stringResource(id = R.string.label_entity_extraction),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            onClick = {
                navigator.navigate(EntityExtractionScreenDestination.route)
            },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeDestinationCard(
    label: @Composable () -> Unit,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.height(150.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.weight(1f, true)
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
        ) {
            label()

            Box(modifier = Modifier.weight(1f, true))
        }
    }
}