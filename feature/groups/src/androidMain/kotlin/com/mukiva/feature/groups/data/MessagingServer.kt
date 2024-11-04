package com.mukiva.feature.groups.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException
import java.net.InetAddress
import java.net.ServerSocket
import java.net.Socket

class MessagingServer(
    private val hostAddress: InetAddress,
    private val onMessageReceived: (String) -> Unit
) : Thread(), IConnectionHolder {
    private var mServerSocket: ServerSocket? = null
    private var mSocket: Socket? = null
    private var mClientHandler: ClientHandler? = null

    private val mServerScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        mServerSocket = ServerSocket(PORT)
    }

    override fun run() {
        super.run()
        try {
            Log.d("ConnectionSuccess", "Create host on: $hostAddress")
            mSocket = mServerSocket?.accept()
            Log.d("ConnectionSuccess", "Create Handler")
            mSocket?.let { socket ->
                mClientHandler = startHandleClients(socket)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun startHandleClients(socket: Socket): ClientHandler {
        val clientHandler = ClientHandler(socket)


        clientHandler.receivedMessageFlow
            .onEach(onMessageReceived)
            .launchIn(mServerScope)

        clientHandler.startReceive()

        return clientHandler
    }

    override fun cancel() {
        mClientHandler?.stop()
        mSocket?.close()
        mServerSocket?.close()
    }

    override fun sendMessage(message: String) {
        mClientHandler?.sendMessage(message.toByteArray())
    }

    companion object {
        private const val PORT = 8888
    }
}