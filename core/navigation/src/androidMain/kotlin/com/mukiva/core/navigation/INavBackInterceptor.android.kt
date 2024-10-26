package com.mukiva.core.navigation

actual class PlatformNavInterceptor : INavBackInterceptor {

    private val mListeners = mutableListOf<() -> Unit>()
    var finishAppCallback: () -> Unit = {}

    override fun intercept() {
        mListeners.onEach { listener ->
            listener.invoke()
        }
    }

    override fun addListener(listener: () -> Unit) {
        mListeners.add(listener)
    }

    override fun closeApp() {
        finishAppCallback.invoke()
    }
}