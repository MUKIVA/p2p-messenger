package com.mukiva.feature.groups.presentation

import androidx.lifecycle.ViewModel
import com.mukiva.core.navigation.INavHost
import com.mukiva.feature.groups.data.IConnectionStatus
import com.mukiva.feature.groups.data.P2PService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ChatState(
    val messages: List<String>
)

class ChatViewModel(
    private val p2pService: P2PService,
    private val navHost: INavHost
) : ViewModel(), INavHost by navHost {

    val state: StateFlow<ChatState>
        get() = mState.asStateFlow()

    private val mState = MutableStateFlow(ChatState(messages = emptyList()))

    init {
        p2pService.addMessageListener(::onMessageReceived)
        p2pService.addConnectionStatusListener(::onConnectionChanged)
    }

    fun sendMessage(message: String) {
        p2pService.sendMessage(message)
        onMessageReceived(message)
    }

    private fun onMessageReceived(message: String) = mState.update { oldState ->
        oldState.copy(
            messages = oldState.messages + message
        )
    }

    private fun onConnectionChanged(status: IConnectionStatus) {
        if (status == IConnectionStatus.Lost) {
            p2pService.closeConnection()
            navHost.back()
        }
    }

    override fun back() {
        p2pService.closeConnection()
        navHost.back()
    }

}