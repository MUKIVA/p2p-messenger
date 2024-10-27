package com.mukiva.feature.groups.presentation.state

sealed interface IGroupSearchViewState {

    data object Init : IGroupSearchViewState
    data class Content(
        val list: List<Int>,
        val isSearching: Boolean
    ) : IGroupSearchViewState

}