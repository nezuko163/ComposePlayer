package com.nezuko.domain.model

data class AudioModel(
    val trackInfo: TrackInfo,
    val audioUri: String,
    val date: Long?
)
