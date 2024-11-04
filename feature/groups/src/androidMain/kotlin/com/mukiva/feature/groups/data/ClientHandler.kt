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
import java.net.Socket

class Messenger(
    private val socket: Socket
) {

    val recievedMessageFlow: SharedFlow<String>
        get() = mReceivedMessageFlow.asSharedFlow()

    private val mMessageSenderScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val mInputStream = socket.getInputStream()
    private val mOutputStream = socket.getOutputStream()

    private val mReceivedMessageFlow = MutableSharedFlow<String>()

    init {
        mMessageSenderScope.launch {
            startReceive()
        }
    }

    fun sendMessage(message: ByteArray) {
        mMessageSenderScope.launch {
            mOutputStream.write(message)
        }
    }

    private suspend fun startReceive() = withContext(Dispatchers.IO) {
        val buffer = ByteArray(1024)
        while (true) {
            var bytes: Int
            try {
                bytes = mInputStream.read(buffer)

                if (bytes > 0) {
                    val tmpMessage = String(buffer, 0, bytes)
                    mReceivedMessageFlow.emit(tmpMessage)
                }

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun stop() {
        mMessageSenderScope.cancel()
    }

}