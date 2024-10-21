package com.mukiva.p2pmessenger.main.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mukiva.navigation.IDestination
import com.mukiva.p2pmessenger.main.ui.MainScreen

object MainDestination : IDestination {

    override val screen: @Composable (Modifier, Bundle?) -> Unit
        get() = { modifier, args ->
            MainScreen(modifier, args)
        }

}
