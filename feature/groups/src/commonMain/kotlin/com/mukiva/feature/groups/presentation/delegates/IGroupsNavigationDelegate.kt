package com.mukiva.feature.groups.presentation.delegates

import com.mukiva.core.navigation.INavHost
import com.mukiva.feature.groups.navigation.GroupsSearchDestination

internal interface IGroupsNavigationDelegate {
    fun goAddGroup()
}

internal class GroupsNavigationDelegateImpl(
    private val navHost: INavHost
) : IGroupsNavigationDelegate {
    override fun goAddGroup() {
        navHost.navigate(GroupsSearchDestination)
    }
}