package com.nezuko.data.utils

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.nezuko.domain.model.Audio

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
            queueItem.description.mediaUri.toString()
        )
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

fun metadataToAudio(metadata: MediaMetadataCompat) = Audio(
    -1L,
    metadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE),
    metadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST),
    metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM),
    metadata.getString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI),
    metadata.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI),
    metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION),
    "",
)

fun audioToMediaDescriptionCompat(
    audio: Audio,
    id: Long? = null
): MediaDescriptionCompat {
    val bundle = Bundle().apply {
        putLong("duration", audio.duration)
        if (id != null) {
            putLong("queue_id", id)
        }
    }
    Log.i("ART_URI", "audioToMediaDescriptionCompat: ${audio.artUrl}")
    Log.i("MEDIA_URI", "audioToMediaDescriptionCompat: ${audio.meduaUrl}")
    return MediaDescriptionCompat.Builder()
        .setExtras(bundle)
        .setTitle(audio.title)
        .setSubtitle(audio.artist)
        .setMediaUri(Uri.parse(audio.meduaUrl))
        .setIconUri(Uri.parse(audio.artUrl))
        .setDescription(audio.title)
        .build()
}

fun queueItemToAudio(item: MediaSessionCompat.QueueItem): Audio {
    val duration = item.description.extras?.getLong(MediaMetadataCompat.METADATA_KEY_DURATION, -1) ?: 0
    return Audio(
        -1L,
        item.description.title.toString(),
        item.description.subtitle.toString(),
        "queueItemToAudio: нужно будет вшить в queueItem album",
        item.description.iconUri.run { this?.toString() ?: "" },
        item.description.mediaUri.run { this?.toString() ?: "" },
        duration,
        "queueItemToAudio: owner_id тоже можно вшить",
        queueId = item.queueId
    )

}

fun queueToAudioList(queue: MutableList<MediaSessionCompat.QueueItem>?): ArrayList<Audio> {
    if (queue == null) return arrayListOf()

    return ArrayList(queue.map { item -> queueItemToAudio(item) })
}