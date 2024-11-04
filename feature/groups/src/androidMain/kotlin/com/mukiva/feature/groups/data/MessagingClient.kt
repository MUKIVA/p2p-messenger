package com.mukiva.feature.groups.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class MessagingClient(
    private val hostAddress: String,
    private val onMessageReceived: (String) -> Unit
) : Thread(), IConnectionHolder {

    private var mSocket: Socket = Socket()
    private var mClientHandler: ClientHandler? = null

    private val mClientScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun run() {
        super.run()

        try {
            Log.d("ConnectionSuccess", hostAddress)
            mSocket.bind(null)
            mSocket.connect(
                InetSocketAddress(hostAddress, PORT),
                TIMEOUT_MS
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mClientHandler = startSocketHandle(mSocket)
    }

    override fun cancel() {
        mSocket.close()
        mClientHandler?.stop()
    }

    override fun sendMessage(message: String) {
        mClientHandler?.sendMessage(message.toByteArray())
    }

    private fun startSocketHandle(socket: Socket): ClientHandler {
        val clientHandler = ClientHandler(socket)

        clientHandler.receivedMessageFlow
            .onEach(onMessageReceived)
            .launchIn(mClientScope)

        clientHandler.startReceive()

        return clientHandler
    }

    companion object {
        private val TIMEOUT_MS = 15_000
        private val PORT = 8888
    }
}