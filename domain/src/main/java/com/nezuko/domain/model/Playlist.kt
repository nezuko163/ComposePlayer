package com.nezuko.domain.model

data class Playlist(
    val id: Long = -1,
    val title: String = "",
    val owner_id: String = "",
    val ownerName: String = "",
    val artUrl: String = "",
    val trackList: List<Long> = listOf(),
    val dateCreated: Long = 0,
    val dateModified: Long = 0
)