package com.mukiva.feature.main.presentation.state

import androidx.compose.runtime.Immutable
import com.mukiva.core.navigation.IDestination
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Immutable
data class MainState(
    val navigationStack: ImmutableList<IDestination<*>>
) {

    val onTopNavigationStack: IDestination<*>?
        get() = if (navigationStack.isEmpty()) {
            null
        } else {
            navigationStack.last()
        }

    companion object {

        fun createEmpty(): MainState {
            return MainState(
                navigationStack = persistentListOf()
            )
        }

    }
}