package com.mukiva.p2pmessenger.core.uikit.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mukiva.p2pmessenger.core.uikit.R

@Composable
fun EmptyPlaceholder(
    modifier: Modifier = Modifier,
    info: String = stringResource(R.string.uikit_default_empty_placeholder_info)
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

@Preview(
    showSystemUi = true
)
@Composable
fun EmptyPlaceholderPreview(
    modifier: Modifier = Modifier,
    info: String = stringResource(R.string.uikit_default_empty_placeholder_info)
) {
    EmptyPlaceholder()
}