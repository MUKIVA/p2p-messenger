package com.mukiva.feature.groups.data

import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

class MessegingClient(
    private val hostAddress: InetAddress,
    private val clientHandler: ClientHandler
) : Thread(), IConnectionHolder {

    private val mSocket = Socket()

    override fun run() {
        super.run()
        try {
            mSocket.connect(
                InetSocketAddress(hostAddress.hostAddress ?: "", PORT),
                TIMEOUT_MS
            )

            while (true) {
                clientHandler.startReceive(mSocket)
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun cancel() {
        mSocket.close()
    }

    override fun sendMessage(message: String) {
        clientHandler.sendMessage(mSocket, message.toByteArray())
    }

    companion object {
        private val TIMEOUT_MS = 15_000
        private val PORT = 50001
    }


}