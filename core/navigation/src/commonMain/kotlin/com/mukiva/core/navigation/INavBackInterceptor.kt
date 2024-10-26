package com.mukiva.core.navigation

interface INavBackInterceptor {
    fun intercept()
    fun addListener(listener: () -> Unit)
    fun closeApp()
}

expect class PlatformNavInterceptor : INavBackInterceptor