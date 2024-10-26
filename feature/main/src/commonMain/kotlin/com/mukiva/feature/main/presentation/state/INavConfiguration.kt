package com.mukiva.feature.main.presentation.state

import com.mukiva.core.navigation.IDestination

interface INavConfiguration {
    val startDestination: IDestination<*>
}