package com.mukiva.feature.groups.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class ClientHandler(
    private val socket: Socket
) {

    val receivedMessageFlow: SharedFlow<String>
        get() = mReceivedMessageFlow.asSharedFlow()

    private val mClientHandlerScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val mReceivedMessageFlow = MutableSharedFlow<String>()

    private val mInputStream: InputStream = socket.getInputStream()
    private val mOutputStream: OutputStream = socket.getOutputStream()

    fun sendMessage(message: ByteArray) {
        mClientHandlerScope.launch {
            mOutputStream.write(message)
        }
    }

    fun startReceive() {
        mClientHandlerScope.launch {
            startReceiveSuspend()
        }
    }

    private suspend fun startReceiveSuspend() {
        val buffer = ByteArray(1024)
        try {
            while (socket.isConnected) {
                handle(buffer)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private suspend fun handle(buffer: ByteArray) = withContext(Dispatchers.IO) {
        val bytes = mInputStream.read(buffer)

        if (bytes > 0) {
            val tmpMessage = String(buffer, 0, bytes)
            mReceivedMessageFlow.emit(tmpMessage)
        }
    }

    fun stop() {
        mClientHandlerScope.cancel()
    }

}