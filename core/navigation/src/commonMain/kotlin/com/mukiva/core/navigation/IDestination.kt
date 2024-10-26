package com.mukiva.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface IDestination<in TArg> {

    val screen: @Composable (TArg?, Modifier) -> Unit

}