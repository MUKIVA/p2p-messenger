package com.mukiva.feature.groups.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukiva.feature.groups.data.IGroupsRepository
import com.mukiva.feature.groups.presentation.delegates.IGroupsControlDelegate
import com.mukiva.feature.groups.presentation.state.IGroupsState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class GroupsViewModel(
    groupsControlDelegate: IGroupsControlDelegate,
    groupsRepository: IGroupsRepository
)
    : ViewModel()
    , IGroupsControlDelegate by groupsControlDelegate
{
    val state: StateFlow<IGroupsState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow<IGroupsState>(IGroupsState.Init)

    init {
        viewModelScope.launch(Dispatchers.IO) {

            val job = async {
                delay(3000)
                groupsRepository.getAllGroups()
            }

            val data = job.await()
            mState.update { oldState ->
                IGroupsState.Content(data.toImmutableList())
            }
        }
    }
}