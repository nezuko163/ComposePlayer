package com.nezuko.data.repositoryImpl

import android.content.Context
import android.util.Log
import com.CodeBoy.MediaFacer.AudioGet
import com.CodeBoy.MediaFacer.MediaFacer
import com.nezuko.data.R
import com.nezuko.data.utils.resToUri
import com.nezuko.domain.model.AudioModel
import com.nezuko.domain.model.PlaylistModel
import com.nezuko.domain.model.TrackInfo
import com.nezuko.domain.repository.PlaylistsRepository

class PlaylistsRepositoryImpl(val context: Context) : PlaylistsRepository {
    override fun getAllTracksFromLocalStore() =
        PlaylistModel(
            -1L,
            false,
            "Локальные треки",
            "пидорас",
            getAudios(context),
            context.resToUri(R.drawable.img).toString()
        )

    override fun getAllPlaylistsFromLocal(): ArrayList<PlaylistModel> {
        // пиздец ну тута надо с бд, а пока что просто
//        return arrayListOf(getAllTracksFromLocalStore())
        return arrayListOf()
    }

    fun getAudios(context: Context): ArrayList<AudioModel> {
        val lst_audio = ArrayList<AudioModel>()

        MediaFacer.withAudioContex(context)
            .getAllAudioContent(AudioGet.externalContentUri)
            .forEach {
                if (it.duration == 0L) return@forEach
                val trackInfo = TrackInfo(
                    it.title,
                    it.artist,
                    it.album,
                    it.name,
                    it.duration,
                    it.art_uri.toString()
                )
                val audio = AudioModel(
                    trackInfo,
                    it.assetFileStringUri,
                    it.date_taken
                )
                val TAG = "ALL_AUDIOS"
                Log.i(TAG, "getAudios: ${it.name}")

                lst_audio.add(audio)
            }
        return lst_audio
    }
}