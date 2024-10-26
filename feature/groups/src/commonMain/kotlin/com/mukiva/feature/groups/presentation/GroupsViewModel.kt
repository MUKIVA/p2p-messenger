package com.mukiva.feature.groups.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.feature.groups.presentation.delegates.IGroupsControlDelegate
import com.mukiva.feature.groups.presentation.state.IGroupsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class GroupsViewModel constructor(
    groupsControlDelegate: IGroupsControlDelegate
)
    : ViewModel()
    , IGroupsControlDelegate by groupsControlDelegate
{
    val state: StateFlow<IGroupsState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow<IGroupsState>(IGroupsState.Init)

}