package com.github.bkmbigo.mlkitsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.github.bkmbigo.mlkitsample.ui.screens.NavGraphs
import com.github.bkmbigo.mlkitsample.ui.theme.MLKitSampleTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MLKitSampleTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}


@RootNavGraph(start = true)
@NavGraph
annotation class MainNavGraph(
    val start: Boolean = false
)