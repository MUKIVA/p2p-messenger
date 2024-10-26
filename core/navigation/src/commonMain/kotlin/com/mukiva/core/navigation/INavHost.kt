package com.mukiva.core.navigation

interface INavHost {
    fun navigate(destination: IDestination<*>)
    fun back()
}