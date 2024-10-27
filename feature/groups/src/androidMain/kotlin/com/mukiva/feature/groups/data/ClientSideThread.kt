package com.mukiva.feature.groups.data

import java.io.IOException
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.Socket

class ClientSideThread(
    hostAddress: InetAddress,
    private val onSocketCreated: (Socket) -> Unit
) : Thread() {

    private val socket: Socket = Socket()
    private val mHostAddress: String = hostAddress.hostAddress ?: ""

    override fun run() {
        try {
            socket.connect(InetSocketAddress(mHostAddress, 8888), TIMEOUT_MS)
            onSocketCreated(socket)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        private val TIMEOUT_MS = 15_000
    }

}