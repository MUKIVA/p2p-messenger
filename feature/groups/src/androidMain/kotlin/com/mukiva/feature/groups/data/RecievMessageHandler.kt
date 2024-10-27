package com.mukiva.feature.groups.data

import android.os.Handler
import android.os.Message

class RecievMessageHandler(
    onMessageReceived: (String) -> Unit
) : Handler(object : Callback {

    override fun handleMessage(msg: Message): Boolean {

        when (msg.what) {
            MESSAGE_READ -> {
                val readBuff = msg.obj as ByteArray
                val tmpMsg = String(readBuff, 0, msg.arg1)
                onMessageReceived(tmpMsg)
            }
            else -> return false
        }

        return true
    }

}) {
    companion object {
        const val MESSAGE_READ = 1
    }
}