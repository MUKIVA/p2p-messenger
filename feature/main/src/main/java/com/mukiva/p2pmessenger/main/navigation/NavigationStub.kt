package com.mukiva.p2pmessenger.main.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mukiva.navigation.IDestination
import com.mukiva.p2pmessenger.core.uikit.ui.LoadingPlaceholder
import com.mukiva.p2pmessenger.main.R

object NavigationStub : IDestination {
    override val screen: @Composable (Modifier, Bundle?) -> Unit
        get() = { _, _ ->
            LoadingPlaceholder(
                info = stringResource(R.string.main_loading_placeholder_info)
            )
        }

}