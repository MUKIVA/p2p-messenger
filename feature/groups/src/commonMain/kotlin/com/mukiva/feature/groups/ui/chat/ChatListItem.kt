package com.mukiva.feature.groups.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatListItem(
    text: String,
    modifier: Modifier = Modifier
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center,
    modifier = modifier.fillMaxWidth()
        .background(MaterialTheme.colorScheme.primary)
        .padding(16.dp)
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onPrimary
    )
}