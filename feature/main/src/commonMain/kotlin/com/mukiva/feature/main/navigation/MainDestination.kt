package com.mukiva.feature.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mukiva.core.navigation.IDestination
import com.mukiva.feature.main.ui.MainScreen

object MainDestination : IDestination<Nothing> {

    override val screen: @Composable (Nothing?, Modifier) -> Unit
        get() = { _, modifier ->
            MainScreen(modifier)
        }

}