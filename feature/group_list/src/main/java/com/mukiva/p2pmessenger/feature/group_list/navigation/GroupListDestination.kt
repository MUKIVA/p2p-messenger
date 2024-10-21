package com.mukiva.p2pmessenger.feature.group_list.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mukiva.navigation.IDestination
import com.mukiva.p2pmessenger.feature.group_list.ui.GroupListScreen

object GroupListDestination : IDestination {

    override val screen: @Composable (Modifier, Bundle?) -> Unit
        get() = { modifier, _ ->
            GroupListScreen(
                modifier = modifier
            )
        }
}