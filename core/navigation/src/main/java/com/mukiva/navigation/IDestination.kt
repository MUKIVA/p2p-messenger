package com.mukiva.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface IDestination {

    val screen: @Composable (Modifier, Bundle?) -> Unit

}