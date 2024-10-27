package com.mukiva.feature.groups.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.CircularProgressIndicator
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
import com.mukiva.core.uikit.res.dimensions
import com.mukiva.core.uikit.ui.ErrorPlaceholder
import com.mukiva.core.uikit.ui.LoadingPlaceholder
import com.mukiva.feature.groups.data.IP2PService
import com.mukiva.feature.groups.presentation.ISearchState
import com.mukiva.feature.groups.presentation.SearchViewModel
import com.mukiva.feature.groups.res.Res
import com.mukiva.core.uikit.res.Res as CoreRes
import com.mukiva.feature.groups.res.groups_feature_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GroupSearchScreen(
    modifier: Modifier = Modifier,
    vm: SearchViewModel = koinViewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            GroupSearchTopAppBar(
                title = stringResource(Res.string.groups_feature_name),
                onNavClick = vm::back
            )
        },
        modifier = modifier
    ) { paddingValues ->
        when (val actualState = state) {
            is ISearchState.Content -> GroupSearchContent(
                modifier = Modifier.padding(paddingValues),
                state = actualState,
                onItemClick = vm::connect
            )
            ISearchState.Error ->
                GroupSearchError(Modifier.padding(paddingValues))
            ISearchState.Init ->
                LoadingPlaceholder(Modifier.padding(paddingValues))
            ISearchState.Loading ->
                LoadingPlaceholder(Modifier.padding(paddingValues))
            ISearchState.Unsupported ->
                GroupSearchUnsupported(Modifier.padding(paddingValues))
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun GroupSearchTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavClick: () -> Unit = {}
) = Column(
    modifier = modifier
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavClick) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        }
    )
    HorizontalDivider()
}

@Composable
internal fun GroupSearchError(
    modifier: Modifier = Modifier
) = ErrorPlaceholder(modifier = modifier)

@Composable
internal fun GroupSearchUnsupported(
    modifier: Modifier = Modifier
) = ErrorPlaceholder(modifier = modifier)



@Composable
internal fun GroupSearchContent(
    state: ISearchState.Content,
    modifier: Modifier = Modifier,
    onItemClick: (IP2PService.PeerInfo) -> Unit
) = LazyColumn(
    modifier = modifier
) {
    items(
        count = state.deviceList.size,
        key = { index -> state.deviceList[index].deviceAddress }
    ) { index ->
        val item = state.deviceList[index]
        SearchListItem(
            name = item.deviceName,
            onItemClick = { onItemClick(item) }
        )
        HorizontalDivider()
    }
}

@Composable
internal fun LoadListItem(
    modifier: Modifier = Modifier
) = Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
        .fillMaxWidth()
        .padding(
            CoreRes.dimensions.def_container_horizontal_padding,
            vertical = CoreRes.dimensions.def_container_gap
        )
) {
    CircularProgressIndicator()
}

@Composable
internal fun SearchListItem(
    name: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit = {},
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(
            horizontal = CoreRes.dimensions.def_container_horizontal_padding,
            vertical = CoreRes.dimensions.def_container_gap
        )
) {

    Text(text = name)

    Image(imageVector = Icons.Rounded.Add, contentDescription = null)

}
