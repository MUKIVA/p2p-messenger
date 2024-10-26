package com.mukiva.feature.groups.presentation.delegates

import com.mukiva.feature.groups.models.Group

interface IGroupsControlDelegate {
    fun createGroup(group: Group)
    fun deleteGroup(id: Long)
    fun renameGroup(id: Long, name: String)
}

class GroupsControlDelegate : IGroupsControlDelegate {
    override fun createGroup(group: Group) {
        TODO("Not yet implemented")
    }

    override fun deleteGroup(id: Long) {
        TODO("Not yet implemented")
    }

    override fun renameGroup(id: Long, name: String) {
        TODO("Not yet implemented")
    }
}