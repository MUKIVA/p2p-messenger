package com.mukiva.p2pmessanger

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.mukiva.feature.main.navigation.MainDestination
import org.koin.compose.KoinContext

@Composable
fun App() {
    MaterialTheme {
        KoinContext {
            MainDestination.screen(null, Modifier)
        }
    }
}