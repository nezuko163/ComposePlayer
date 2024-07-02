package com.nezuko.domain.model

data class PlaylistModel(
    val id: Long,
    val isRemote: Boolean,
    val title: String,
    val owner: String,
    var tracksList: ArrayList<AudioModel>,
    val artUriString: String
)