package com.mukiva.feature.groups.data

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class MessegingServer(
    private val clientHandler: ClientHandler
) : Thread(), IConnectionHolder {
    private var mServerSocket: ServerSocket? = null

    override fun run() {
        super.run()
        try {
            mServerSocket = ServerSocket()
            mServerSocket?.reuseAddress = true
            mServerSocket?.bind(InetSocketAddress(50001))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        startHandleClients()
    }

    private fun startHandleClients() {
        try {
            while (true) {
                // handle Messaging
                val socket = mServerSocket?.accept() ?: continue
                clientHandler.startReceive(socket)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun cancel() {
        mServerSocket?.close()
    }

    override fun sendMessage(message: String) {
        clientHandler.sendMessage(Socket(), message.toByteArray())
    }
}