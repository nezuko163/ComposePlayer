package com.nezuko.domain.model

data class UserProfile(
    val id: String,
    val userName: String,
    val artUrl: String,
    val playlistList: List<Long>
)
