package com.nezuko.domain.model

data class TrackInfo(
    val title: String,
    val artist: String,
    val album: String?,
    val description: String?,
    val duration: Long,
    val artUri: String
)
