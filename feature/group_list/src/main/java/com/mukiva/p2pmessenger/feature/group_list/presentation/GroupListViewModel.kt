package com.mukiva.p2pmessenger.feature.group_list.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GroupListViewModel : ViewModel() {

    val state: StateFlow<IGroupListState>
        get() = TODO()

    private val mState = MutableStateFlow<IGroupListState>(IGroupListState.Loading)

    init {

        // TODO Load Groups!!

    }
}