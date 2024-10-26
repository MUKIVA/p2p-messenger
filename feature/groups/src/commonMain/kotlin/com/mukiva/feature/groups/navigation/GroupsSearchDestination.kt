package com.mukiva.feature.groups.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mukiva.core.navigation.IDestination
import com.mukiva.feature.groups.ui.search.GroupSearchScreen

object GroupsSearchDestination : IDestination<Nothing> {

    override val screen: @Composable (Nothing?, Modifier) -> Unit
        get() = { _, modifier ->
            GroupSearchScreen(modifier)
        }

}