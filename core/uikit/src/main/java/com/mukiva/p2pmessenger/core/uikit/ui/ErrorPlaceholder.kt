package com.mukiva.p2pmessenger.core.uikit.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukiva.p2pmessenger.core.uikit.R

@Composable
fun ErrorPlaceholder(
    modifier: Modifier = Modifier,
    info: String = stringResource(R.string.uikit_default_error_placeholder_info),
    buttonText: String = stringResource(R.string.uikit_default_retry_button_text),
    onButtonClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = Icons.Rounded.Warning,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.error),
            modifier = Modifier
                .size(250.dp)
        )
        Text(
            text = info,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(dimensionResource(R.dimen.def_container_gap)))
        Button(onClick = onButtonClick) {
            Text(
                text = buttonText,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(
    showSystemUi = true
)
@Composable
fun ErrorPlaceholderPreview() {
    ErrorPlaceholder()
}