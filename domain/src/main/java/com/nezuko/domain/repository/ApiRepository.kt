package com.nezuko.domain.repository

interface ApiRepository {
    fun registerProfile(
        uid: String,
        username: String,
        photoUrl: String,
        playlistsList: ArrayList<Long>
    )
}