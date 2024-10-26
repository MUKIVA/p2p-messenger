package com.mukiva.feature.groups.data

import com.mukiva.feature.groups.models.Group

interface IGroupsRepository {

    fun getAllGroups(): List<Group>

    fun createGroup(name: String): Long

    fun deleteGroup(id: Long)

}