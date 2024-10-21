package com.mukiva.p2pmessenger.main.ui

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mukiva.p2pmessenger.main.presentation.MainViewModel

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    args: Bundle? = null,
    viewModel: MainViewModel
) {
    val state by viewModel.state.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        state.screen(Modifier, args)
    }
}