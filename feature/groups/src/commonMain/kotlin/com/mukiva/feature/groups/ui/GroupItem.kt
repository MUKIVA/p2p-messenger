package com.mukiva.feature.groups.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GroupItem(
    groupName: String,
    modifier: Modifier = Modifier,
    onDelete: () -> Unit
) = Row(
    modifier = modifier
        .fillMaxWidth()
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