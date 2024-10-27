package com.mukiva.feature.groups.data

import android.os.Handler
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class MessageSender(
    private val socket: Socket,
    private val handler: RecievMessageHandler
) : Thread() {

    private val mInputStream: InputStream = socket.getInputStream()
    private val mOutputStream: OutputStream = socket.getOutputStream()

    override fun run() {
        val buffer = ByteArray(1024)
        var bytes: Int
        while (socket != null) {
            try {
                bytes = mInputStream.read(buffer)
                if (bytes > 0) {
                    handler.obtainMessage(
                        RecievMessageHandler.MESSAGE_READ,
                        bytes, -1, buffer
                    ).sendToTarget()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun write(bytes: ByteArray) {
        try {
            mOutputStream.write(bytes)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}