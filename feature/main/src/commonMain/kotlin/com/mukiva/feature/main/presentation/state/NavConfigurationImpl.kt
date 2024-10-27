package com.mukiva.feature.main.presentation.state

import com.mukiva.core.navigation.IDestination
import com.mukiva.feature.groups.navigation.ChatDestination

internal object NavConfigurationImpl : INavConfiguration {
    override val startDestination: IDestination<*>
        get() = ChatDestination
}