package com.mukiva.feature.main.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.core.navigation.IDestination
import com.mukiva.feature.main.presentation.state.INavConfiguration
import com.mukiva.feature.main.presentation.state.MainState
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


internal class MainViewModel(
    private val navConfiguration: INavConfiguration
) : ViewModel() {

    val state: StateFlow<MainState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow(MainState.createEmpty())

    init {
        initStartNavState()
    }

    fun navigate(destination: IDestination<*>) =  mState.update { oldState ->
        oldState.copy(
            navigationStack = (oldState.navigationStack + destination)
                .toImmutableList()
        )
    }

    fun back() = mState.update { oldState ->
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