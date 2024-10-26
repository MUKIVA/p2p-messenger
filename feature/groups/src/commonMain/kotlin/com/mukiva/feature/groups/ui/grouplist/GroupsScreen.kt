package com.mukiva.feature.groups.ui.grouplist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mukiva.core.uikit.ui.EmptyPlaceholder
import com.mukiva.core.uikit.ui.ErrorPlaceholder
import com.mukiva.feature.groups.presentation.GroupsViewModel
import com.mukiva.feature.groups.presentation.state.IGroupsState
import com.mukiva.feature.groups.res.Res
import com.mukiva.feature.groups.res.groups_feature_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.mukiva.core.uikit.ui.LoadingPlaceholder
import com.mukiva.feature.groups.res.groups_feature_empty_placeholder_text
import com.mukiva.feature.groups.res.groups_feature_error_placeholder_text
import com.mukiva.feature.groups.res.groups_feature_loading_placeholder_text

@Composable
internal fun GroupsScreen(
    modifier: Modifier = Modifier,
    vm: GroupsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            GroupsTopAppBar(
                title = stringResource(Res.string.groups_feature_name),
                onAddChat = vm::goAddGroup
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when (val actualState = state) {
            is IGroupsState.Content ->
                GroupsScreenContent(actualState, Modifier.padding(paddingValues))
            IGroupsState.Error ->
                GroupsScreenError(Modifier.padding(paddingValues))
            IGroupsState.Init ->
                GroupsScreenInit(Modifier.padding(paddingValues))
            IGroupsState.Loading ->
                GroupsScreenLoading(Modifier.padding(paddingValues))
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun GroupsTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onAddChat: () -> Unit = {}
) = Column(modifier = modifier) {
    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = onAddChat) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null
                )
            }
        }
    )
    HorizontalDivider()
}


@Composable
internal fun GroupsScreenContent(
    state: IGroupsState.Content,
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .fillMaxSize()
) {
    when (state.chats.isEmpty()) {
        true -> EmptyPlaceholder(
            info = stringResource(Res.string.groups_feature_empty_placeholder_text)
        )
        false -> GroupContentList(state.chats)
    }
}

@Composable
internal fun GroupsScreenInit(
    modifier: Modifier = Modifier
) {
    LoadingPlaceholder(
        modifier = modifier,
        info = stringResource(Res.string.groups_feature_loading_placeholder_text)
    )
}

@Composable
internal fun GroupsScreenLoading(
    modifier: Modifier = Modifier
) {
    LoadingPlaceholder(
        modifier = modifier,
        info = stringResource(Res.string.groups_feature_loading_placeholder_text)
    )
}

@Composable
internal fun GroupsScreenError(
    modifier: Modifier = Modifier
) {
    ErrorPlaceholder(
        modifier = modifier,
        info = stringResource(Res.string.groups_feature_error_placeholder_text)
    )
}