package com.mukiva.feature.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mukiva.feature.main.presentation.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    vm: MainViewModel = koinViewModel()
) {

    val state by vm.state.collectAsState()

    val topDestination = state.onTopNavigationStack

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        topDestination?.screen?.invoke(null, Modifier)
    }
}