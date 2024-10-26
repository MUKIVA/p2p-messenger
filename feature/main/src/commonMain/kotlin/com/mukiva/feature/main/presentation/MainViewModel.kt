package com.mukiva.feature.main.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.core.navigation.IDestination
import com.mukiva.core.navigation.INavBackInterceptor
import com.mukiva.core.navigation.INavHost
import com.mukiva.feature.main.presentation.state.INavConfiguration
import com.mukiva.feature.main.presentation.state.MainState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

internal class MainViewModel(
    private val navConfiguration: INavConfiguration,
    private val navInterceptor: INavBackInterceptor
) : ViewModel(), INavHost {

    val state: StateFlow<MainState>
        get() = mState

    private val mState = MutableStateFlow(MainState.createEmpty())

    init {
        initStartNavState()

        navInterceptor.addListener(::back)
    }

    override fun navigate(destination: IDestination<*>) = mState.update { oldState ->
        oldState.copy(
            navigationStack = (oldState.navigationStack + destination)
                .toImmutableList()
        )
    }

    override fun back() = mState.update { oldState ->
        if (oldState.navigationStack.size == 1) {
            navInterceptor.closeApp()
            return@update oldState
        }

        oldState.copy(
            navigationStack = oldState.navigationStack.take(oldState.navigationStack.lastIndex)
                .toImmutableList()
        )
    }

    private fun initStartNavState() = mState.update { oldState ->
        oldState.copy(
            navigationStack = persistentListOf(navConfiguration.startDestination)
        )
    }
}