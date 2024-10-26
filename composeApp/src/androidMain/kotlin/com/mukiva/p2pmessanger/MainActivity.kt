package com.mukiva.p2pmessanger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import com.mukiva.core.navigation.INavBackInterceptor
import com.mukiva.core.navigation.PlatformNavInterceptor
import org.koin.android.ext.android.inject



class MainActivity : ComponentActivity() {

    private val mNavInterceptor by inject<PlatformNavInterceptor>()

    class BackPressedCallback(
        private val interceptor: INavBackInterceptor
    ) : OnBackPressedCallback(enabled = true) {
        override fun handleOnBackPressed() {
            interceptor.intercept()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher
            .addCallback(BackPressedCallback(mNavInterceptor))

        mNavInterceptor.finishAppCallback = ::finish

        setContent {
            App()
        }
    }
}
