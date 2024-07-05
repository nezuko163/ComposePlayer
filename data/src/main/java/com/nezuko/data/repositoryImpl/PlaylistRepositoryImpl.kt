package com.nezuko.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer
import com.nezuko.data.R
import com.nezuko.data.utils.resToUri
import com.nezuko.domain.model.Audio
import com.nezuko.domain.model.Playlist
import com.nezuko.domain.repository.PlaylistRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher
) : PlaylistRepository {
    override fun getAllLocalTracks() = Playlist(
        -1L,
        "Файлы на устройстве",
        "",
        "Пидорас",
        context.resToUri(R.drawable.img).toString(),
        listOf(),
    )

    override suspend fun getPlaylists(): ArrayList<Playlist> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrackListByPlaylistId(id: Long) = withContext(ioDispatcher) {
        if (id == -1L) {
            Result.success(getAudios())
        } else {
            Result.failure(Exception())
        }
    }

    private fun getAudios(): ArrayList<Audio> {
        val lst_audio = ArrayList<Audio>()

        MediaFacer.withAudioContex(context)
            .getAllAudioContent(AudioGet.externalContentUri)
            .forEach {
                if (it.duration == 0L) return@forEach
                val audio = Audio(
                    -1L,
                    it.title,
                    it.artist,
                    it.album,
                    it.art_uri.toString(),
                    it.assetFileStringUri,
                    it.duration,
                    "",
                    it.date_added
                )
                val TAG = "ALL_AUDIOS"
                Log.i(TAG, "getAudios: ${it.name}")

                lst_audio.add(audio)
            }
        return lst_audio
    }
}