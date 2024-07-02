package com.nezuko.data.utils

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log

fun metadataBuilder(queueItem: MediaSessionCompat.QueueItem): MediaMetadataCompat.Builder? {

    val duration = queueItem.description.extras?.getLong("duration") ?: return null
    val id = queueItem.description.extras?.getLong("queue_id") ?: return null

    Log.i("MAIN_ACTIVITY", "metadataBuilder: $duration")

    val builder = MediaMetadataCompat.Builder()
        .putString(
            MediaMetadataCompat.METADATA_KEY_ART_URI,
            queueItem.description.iconUri.toString()
        )
        .putString(
            MediaMetadataCompat.METADATA_KEY_MEDIA_URI,
            queueItem.description.mediaUri.toString())
        .putString(
            MediaMetadataCompat.METADATA_KEY_TITLE,
            queueItem.description.title.toString()
        )
        .putString(
            MediaMetadataCompat.METADATA_KEY_ARTIST,
            queueItem.description.subtitle.toString()
        )
        .putString(
            MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION,
            queueItem.description.description.toString()
        )
        .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, id)
        .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
    return builder
}