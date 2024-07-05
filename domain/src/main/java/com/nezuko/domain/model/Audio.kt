package com.nezuko.domain.model

data class Audio(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val artUrl: String,
    val meduaUrl: String,
    val duration: Long,
    val owner_id: String,
    val dateAdded: Long = 0
)
