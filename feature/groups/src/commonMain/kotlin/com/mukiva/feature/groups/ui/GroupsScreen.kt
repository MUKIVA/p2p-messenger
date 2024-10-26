package com.mukiva.feature.groups.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mukiva.feature.groups.presentation.GroupsViewModel
import com.mukiva.feature.groups.presentation.state.IGroupsState
import com.mukiva.feature.groups.res.Res
import com.mukiva.feature.groups.res.groups_feature_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import com.mukiva.core.uikit.ui.LoadingPlaceholder

@Composable
internal fun GroupsScreen(
    modifier: Modifier = Modifier,
    vm: GroupsViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = { GroupsTopAppBar(title = stringResource(Res.string.groups_feature_name)) },
        modifier = Modifier
    ) { paddingValues ->
        when (state) {
            is IGroupsState.Content -> TODO()
            IGroupsState.Error -> TODO()
            IGroupsState.Init -> TODO()
            IGroupsState.Loading -> TODO()
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun GroupsTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
) = TopAppBar(
    modifier = modifier,
    title = { Text(text = title) }
)


@Composable
internal fun GroupsScreenContent(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .fillMaxSize()
) {
    Text("Groups")
}

@Composable
internal fun GroupsScreenInit(
    modifier: Modifier = Modifier
) {
    LoadingPlaceholder()
}