package com.mukiva.p2pmessenger.feature.group_list.presentation

import com.mukiva.p2pmessenger.feature.group_list.domain.Group

sealed interface IGroupListState {

    data object Loading : IGroupListState
    data object Error : IGroupListState
    data class Content(
        val groups: List<Group>,
    ) : IGroupListState

}