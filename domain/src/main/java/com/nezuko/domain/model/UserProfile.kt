package com.nezuko.domain.model

data class UserProfile(
    val id: String="",
    val userName: String="",
    var artUrl: String = "",
    var backgroundArtUrl: String = "",
    val likedTracks: List<Long> = listOf(),
    val playlistList: List<Long> = listOf()
) {
    fun toMap(): Map<String, Any> =
        mapOf(
            "id" to id,
            "userName" to userName,
            "artUrl" to artUrl,
            "backgroundArtUrl" to backgroundArtUrl,
            "likedTracks" to likedTracks,
            "playlistList" to playlistList,
        )
}
