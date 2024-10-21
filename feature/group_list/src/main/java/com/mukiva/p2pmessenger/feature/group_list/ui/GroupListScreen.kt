package com.mukiva.p2pmessenger.feature.group_list.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.mukiva.p2pmessenger.core.uikit.ui.LoadingPlaceholder
import com.mukiva.p2pmessenger.feature.group_list.presentation.GroupListViewModel
import com.mukiva.p2pmessenger.feature.group_list.presentation.IGroupListState

@Composable
fun GroupListScreen(
    modifier: Modifier = Modifier,
    viewModel: GroupListViewModel
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is IGroupListState.Content -> TODO()
        IGroupListState.Error -> {}
        IGroupListState.Loading -> LoadingPlaceholder()
    }
}