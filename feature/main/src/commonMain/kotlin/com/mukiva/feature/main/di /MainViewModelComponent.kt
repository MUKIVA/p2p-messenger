package com.mukiva.feature.main.di

import com.mukiva.feature.main.presentation.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodel = module {

    viewModelOf(::MainViewModel)

}