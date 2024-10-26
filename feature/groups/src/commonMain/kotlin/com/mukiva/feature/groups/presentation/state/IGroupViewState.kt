package com.mukiva.feature.groups.presentation.state

import androidx.compose.runtime.Immutable
import com.mukiva.feature.groups.models.Group
import kotlinx.collections.immutable.ImmutableList


@Immutable
sealed interface IGroupsState {

    @Immutable
    data object Init : IGroupsState

    @Immutable
    data object Error : IGroupsState

    @Immutable
    data object Loading : IGroupsState

    @Immutable
    data class Content(
        val chats: ImmutableList<Group>
    ) : IGroupsState
}