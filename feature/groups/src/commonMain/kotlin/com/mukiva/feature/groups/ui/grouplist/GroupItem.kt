package com.mukiva.feature.groups.ui.grouplist

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mukiva.core.uikit.res.Res
import com.mukiva.core.uikit.res.dimensions

@Composable
fun GroupItem(
    groupName: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit,
    onItemClick: () -> Unit,
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
        .fillMaxWidth()
        .clickable { onItemClick() }
        .padding(horizontal = Res.dimensions.def_container_horizontal_padding),
) {
    Text(
        text = groupName,
        modifier = Modifier.fillMaxHeight()
    )
    IconButton(onClick = onDelete) {
        Image(
            imageVector = Icons.Rounded.Delete,
            contentDescription = null
        )
    }
}