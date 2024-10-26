package com.mukiva.feature.groups.di

import com.mukiva.feature.groups.presentation.GroupsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewmodel = module {

    viewModelOf(::GroupsViewModel)

}
