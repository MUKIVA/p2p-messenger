package com.mukiva.feature.groups.data

import com.mukiva.feature.groups.models.Group

class MockGroupsRepository : IGroupsRepository {

    private var mGroupsList = mutableListOf(
        Group(0, "Sample One", emptyList()),
        Group(1, "Sample Two", emptyList()),
        Group(2, "Sample Third", emptyList()),
    )

    override fun getAllGroups(): List<Group> {
        return mGroupsList
    }

    override fun createGroup(name: String): Long {
        TODO()
    }

    override fun deleteGroup(id: Long) {
        TODO()
    }

}