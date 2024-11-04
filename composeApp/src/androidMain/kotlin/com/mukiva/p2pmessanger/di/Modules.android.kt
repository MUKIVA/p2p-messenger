package com.mukiva.p2pmessanger.di

import com.mukiva.core.navigation.INavBackInterceptor
import com.mukiva.core.navigation.PlatformNavInterceptor
import com.mukiva.feature.groups.data.P2PService
import com.mukiva.feature.groups.data.WifiP2PManagerFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModules: Module
    get() = module {
        singleOf(::PlatformNavInterceptor)
            .bind(INavBackInterceptor::class)
        single { P2PService(get()) }
        factoryOf(::WifiP2PManagerFactory)
    }