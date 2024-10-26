package com.mukiva.feature.main.di

import com.mukiva.feature.main.presentation.state.INavConfiguration
import com.mukiva.feature.main.presentation.state.NavConfigurationImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val mainCommonComponent = module {
    single { NavConfigurationImpl }.bind(INavConfiguration::class)
}