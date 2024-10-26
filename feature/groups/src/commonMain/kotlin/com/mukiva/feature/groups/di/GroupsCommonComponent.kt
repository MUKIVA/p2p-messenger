package com.mukiva.feature.groups.di

import com.mukiva.feature.groups.data.IGroupsRepository
import com.mukiva.feature.groups.data.MockGroupsRepository
import com.mukiva.feature.groups.presentation.delegates.GroupsControlDelegate
import com.mukiva.feature.groups.presentation.delegates.IGroupsControlDelegate
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val common = module {

    factory<IGroupsControlDelegate> {
        GroupsControlDelegate()
    }

    singleOf(::MockGroupsRepository).bind(IGroupsRepository::class)

}