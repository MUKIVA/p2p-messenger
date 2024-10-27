package com.mukiva.feature.groups.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mukiva.feature.groups.data.IP2PService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatState(
    val messages: List<String>
)

class ChatViewModel(
    private val p2pService: IP2PService
) : ViewModel() {

    val state: StateFlow<ChatState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow(ChatState(messages = emptyList()))

    init {
        p2pService.addMessageListener(::onMessageReceived)
    }

    fun sendMessage(message: String) {
        viewModelScope.launch(Dispatchers.Default) {
            p2pService.sendMessage(message)
        }
    }

    private fun onMessageReceived(message: String) = mState.update { oldState ->
        oldState.copy(
            messages = oldState.messages + message
        )
    }
}