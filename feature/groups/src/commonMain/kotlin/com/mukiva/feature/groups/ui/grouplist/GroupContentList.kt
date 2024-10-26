package com.mukiva.feature.groups.ui.grouplist

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mukiva.feature.groups.models.Group

@Composable
fun GroupContentList(
    groupList: List<Group>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(
            count = groupList.size,
            key = { index -> groupList[index].id }
        ) { index ->
            val item = groupList[index]
            GroupItem(
                groupName = item.name,
                onDelete = {},
                onItemClick = {}
            )
            HorizontalDivider()
        }
    }
}