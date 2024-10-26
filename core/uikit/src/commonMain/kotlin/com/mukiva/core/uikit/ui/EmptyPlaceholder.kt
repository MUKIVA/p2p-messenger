package com.mukiva.core.uikit.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mukiva.core.uikit.res.Res
import com.mukiva.core.uikit.res.uikit_default_empty_placeholder_info
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyPlaceholder(
    modifier: Modifier = Modifier,
    info: String = stringResource(Res.string.uikit_default_empty_placeholder_info)
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = info,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}