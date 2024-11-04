package com.mukiva.feature.groups.data

interface IConnectionHolder {
    fun cancel()
    fun start()
    fun sendMessage(message: String)
}