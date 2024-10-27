package com.mukiva.feature.groups.di

import com.mukiva.feature.groups.presentation.ChatViewModel
import com.mukiva.feature.groups.presentation.GroupsViewModel
import com.mukiva.feature.groups.presentation.SearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodel = module {

    viewModelOf(::GroupsViewModel)
    viewModelOf(::SearchViewModel)
    viewModelOf(::ChatViewModel)
}
