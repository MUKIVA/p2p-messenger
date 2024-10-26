package com.mukiva.feature.main.di

import com.mukiva.core.navigation.INavHost
import com.mukiva.feature.main.presentation.MainViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val viewmodel = module {

    singleOf(::MainViewModel)
        .bind(INavHost::class)

}