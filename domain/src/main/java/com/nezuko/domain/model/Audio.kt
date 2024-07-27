package com.nezuko.domain.model

data class Audio(
    val id: Long = -1L,
    val title: String = "без названия",
    val artist: String = "неизвестен",
    val album: String = "zhopa",
    val artUrl: String = "",
    val meduaUrl: String = "",
    val duration: Long = -1L,
    val owner_id: String = "",
    val dateAdded: Long = 0,
    var queueId: Long = -1
) {
    fun setQueueID(id: Long): Audio {
        queueId = id
        return this
    }
}
