package com.mukiva.p2pmessenger.feature.group_list.domain

data class Group(
    val id: Long,
    val name: String,
    val listenerCount: Int
)