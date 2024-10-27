package com.mukiva.p2pmessanger

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import com.mukiva.core.navigation.INavBackInterceptor
import com.mukiva.core.navigation.PlatformNavInterceptor
import com.mukiva.feature.groups.data.P2PService
import org.koin.android.ext.android.inject



class MainActivity : ComponentActivity() {

    private val mNavInterceptor by inject<PlatformNavInterceptor>()
    private val mP2PService by inject<P2PService>()

    class BackPressedCallback(
        private val interceptor: INavBackInterceptor
    ) : OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            interceptor.intercept()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupNavigation()
        mP2PService.setupNetworkConfiguration(this)

        setContent {
            App()
        }
    }

    override fun onResume() {
        super.onResume()
        mP2PService.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        mP2PService.onPause(this)
    }

    private fun setupNavigation() {
        onBackPressedDispatcher
            .addCallback(BackPressedCallback(mNavInterceptor))

        mNavInterceptor.finishAppCallback = ::finish
    }
}
