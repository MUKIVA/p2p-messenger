package com.mukiva.feature.groups.data

import java.io.IOException
import java.net.ServerSocket
import java.net.Socket

class ServerSideThread(
    private val onSocketCreated: (Socket) -> Unit
) : Thread() {

    private lateinit var socket: Socket
    private lateinit var mServerSocket: ServerSocket

    override fun run() {
        try {
            mServerSocket = ServerSocket(8888)
            socket = mServerSocket.accept()
            onSocketCreated(socket)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}