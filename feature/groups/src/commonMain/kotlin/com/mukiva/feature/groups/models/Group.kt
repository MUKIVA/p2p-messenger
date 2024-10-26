package com.mukiva.feature.groups.models

data class Group(
    val id: Long,
    val name: String,
    val members: List<User>
)