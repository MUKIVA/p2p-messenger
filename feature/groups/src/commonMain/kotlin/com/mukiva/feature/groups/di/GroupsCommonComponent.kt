package com.mukiva.feature.groups.di

import com.mukiva.feature.groups.presentation.delegates.GroupsControlDelegate
import com.mukiva.feature.groups.presentation.delegates.IGroupsControlDelegate
import org.koin.dsl.module

val common = module {

    factory<IGroupsControlDelegate> {
        GroupsControlDelegate()
    }

}