package com.mukiva.feature.groups.ui.chat

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mukiva.feature.groups.presentation.ChatState
import com.mukiva.feature.groups.presentation.ChatViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    vm: ChatViewModel = koinViewModel()
) {

    val state by vm.state.collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = { MessageInput(
            onSend = vm::sendMessage
        ) }
    ) { paddingValues ->
        ChatList(state, Modifier.padding(paddingValues))
    }
}


@Composable
fun ChatList(
    state: ChatState,
    modifier: Modifier
) = LazyColumn(
    modifier = modifier.fillMaxSize()
        .padding(horizontal = 16.dp)
) {
    items(
        count = state.messages.size
    ) { index ->
        val item = state.messages[index]
        ChatListItem(item)
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Composable
fun MessageInput(
    modifier: Modifier = Modifier,
    onSend: (String) -> Unit
) = Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween,
    modifier = modifier
) {

    var text by rememberSaveable { mutableStateOf("") }

    TextField(
        value = text,
        onValueChange = { new -> text = new },
        label = { Text("Enter the text") }
    )

    IconButton(onClick = { onSend(text) }) {
        Icon(Icons.AutoMirrored.Rounded.Send, contentDescription = null)
    }
}
