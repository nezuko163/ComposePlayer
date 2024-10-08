package com.nezuko.data.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.ResultReceiver
import android.service.media.MediaBrowserService
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.MediaSessionCompat.QueueItem
import android.support.v4.media.session.PlaybackStateCompat
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import androidx.media.AudioManagerCompat
import androidx.media.MediaBrowserServiceCompat
import androidx.media.session.MediaButtonReceiver
import com.nezuko.data.R
import com.nezuko.data.utils.NotificationHelper
import com.nezuko.data.utils.metadataBuilder


class MediaPlaybackService : MediaBrowserServiceCompat() {

    val TAG = "SERVICE_MEDIA_AUDIO"
//    val CHANNEL_ID = "1488"

    private lateinit var mediaSession: MediaSessionCompat
    private lateinit var audioFocusRequest: AudioFocusRequest

    //    private lateinit var noisyReceiver: NoisyReceiver
    private lateinit var player: Player


    private val handler = Handler(Looper.getMainLooper())
    private var currentQueueItemId = -1
    private val list = ArrayList<QueueItem>()

    private val noisyReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            try {
                mediaSession.controller.transportControls.pause()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
    private val playerPositionRunnable = PlayerPosition()

    private val onCompletionListener = MediaPlayer.OnCompletionListener {
        val repeatMode = mediaSession.controller.repeatMode
        Log.i(TAG, "oncompl Listener: $repeatMode")
        when (repeatMode) {
            PlaybackStateCompat.REPEAT_MODE_ONE -> callback.onPlay()
            PlaybackStateCompat.REPEAT_MODE_ALL -> callback.onSkipToNext()
            else -> {
                if (currentQueueItemId == list.size) return@OnCompletionListener
                callback.onStop()
            }
        }
    }


    private val onErrorListener =
        MediaPlayer.OnErrorListener { mp: MediaPlayer?, what: Int, extra: Int ->
            handler.removeCallbacks(playerPositionRunnable)

            false
        }


    private val afChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS, AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                mediaSession.controller.transportControls.pause()
            }

            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> player.mediaPlayer.setVolume(
                0.3f,
                0.3f
            )

            AudioManagerCompat.AUDIOFOCUS_GAIN -> player.mediaPlayer.setVolume(1.0f, 1.0f)
        }
    }

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            val keyEvent: KeyEvent? =
                // SDK 33 and up
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mediaButtonEvent?.getParcelableExtra(
                        Intent.EXTRA_KEY_EVENT,
                        KeyEvent::class.java
                    )
                } else {
                    mediaButtonEvent?.getParcelableExtra(
                        Intent.EXTRA_KEY_EVENT
                    )
                }
            keyEvent?.let { event ->
                when (event.keyCode) {
                    KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE -> {
                        if (player.isPlaying()) onPause()
                        else onPlay()
                    }

                    KeyEvent.KEYCODE_MEDIA_PLAY -> onPlay()
                    KeyEvent.KEYCODE_MEDIA_PAUSE -> onPause()
                    KeyEvent.KEYCODE_MEDIA_PREVIOUS -> onSkipToPrevious()
                    KeyEvent.KEYCODE_MEDIA_NEXT -> onSkipToNext()
                }
            }


            return super.onMediaButtonEvent(mediaButtonEvent)
        }


        override fun onSetRepeatMode(repeatMode: Int) {
            super.onSetRepeatMode(repeatMode)

            mediaSession.setRepeatMode(repeatMode)
        }

        override fun onPlay() {
            super.onPlay()

            Log.i(TAG, "onPlay: called onplay")
            val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager

            audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN).run {
                setOnAudioFocusChangeListener(afChangeListener)
                setAudioAttributes(AudioAttributes.Builder().run {
                    setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    build()
                })
                build()
            }
            val result = am.requestAudioFocus(audioFocusRequest)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                startService(Intent(applicationContext, MediaBrowserService::class.java))
                mediaSession.isActive = true
                setMediaPlaybackState(PlaybackStateCompat.STATE_PLAYING)
                player.play()
                registerReceiver(
                    noisyReceiver,
                    IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
                )
//                val thread = Thread(playerPosition)
//                Thread(Player) start ()
                refreshNotification()

            }
        }

        override fun onStop() {
            super.onStop()
            Log.i(TAG, "onStop: called onstop")
            val am = getSystemService(Context.AUDIO_SERVICE) as AudioManager
            try {
                am.abandonAudioFocusRequest(audioFocusRequest)
                unregisterReceiver(noisyReceiver)
            } catch (_: Exception) {
            }
            stopSelf()
            mediaSession.isActive = false
            player.stop()
            stopForeground(NOTIFIC_CHANNEL_ID)
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            super.onCustomAction(action, extras)

            action?.let {
                when (it) {
                    "clear" -> clearQueue()
                    "playOrPause" -> playOrPause()
                }
            }
        }

        private fun playOrPause() {
            if (player.isPlaying()) onPause()
            else onPlay()
        }

        override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
            super.onCommand(command, extras, cb)
        }

        override fun onPause() {
            super.onPause()

            Log.i(TAG, "onPause: called onpause")

            player.pause()
            mediaSession.isActive = true
//            unregisterReceiver(noisyReceiver)
            refreshNotification()
            setMediaPlaybackState(PlaybackStateCompat.STATE_PAUSED)
//            stopForeground(NOTIFIC_CHANNEL_ID)
        }

        override fun onSeekTo(pos: Long) {
            super.onSeekTo(pos)

            player.seekTo(pos.toInt())
        }

        override fun onSkipToQueueItem(id: Long) {
            super.onSkipToQueueItem(id)
            Log.i(TAG, "onSkipToQueueItem: $id")

            if (id >= list.size || id < 0) return

            currentQueueItemId = id.toInt()

            onPrepare()
        }

        override fun onSkipToPrevious() {
            super.onSkipToPrevious()
            Log.i(TAG, "onSkipToPrevious: $currentQueueItemId")
            onSkipToQueueItem((--currentQueueItemId).toLong())
        }

        override fun onSkipToNext() {
            super.onSkipToNext()
            Log.i(TAG, "onSkipToNext: $currentQueueItemId")
            if (currentQueueItemId == list.size - 1) {
                if (mediaSession.controller.repeatMode == PlaybackStateCompat.REPEAT_MODE_ALL) {
                    onSkipToQueueItem(0)
                } else return
            }

            onSkipToQueueItem((++currentQueueItemId).toLong())

        }

        override fun onPrepare() {
            super.onPrepare()
            val track = list[currentQueueItemId]
            val state = mediaSession.controller.playbackState.state

            val uri = track.description.mediaUri ?: return

            player.other(uri)

            setMediaMetadata()
            setMediaPlaybackState(PlaybackStateCompat.STATE_NONE)
            refreshNotification()

            if (state == PlaybackStateCompat.STATE_SKIPPING_TO_QUEUE_ITEM ||
                state == PlaybackStateCompat.STATE_PLAYING
            ) {
                onPlay()
            }
        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?, index: Int) {
            super.onAddQueueItem(description, index)

            val item = QueueItem(description, index.toLong())
            list.add(index, item)

            mediaSession.setQueue(list)

        }

        override fun onAddQueueItem(description: MediaDescriptionCompat?) {
            super.onAddQueueItem(description)
            onAddQueueItem(description, list.size)
        }

    }


    private fun setMediaPlaybackState(state: Int, bundle: Bundle = Bundle()) {
        try {
            val duration = player.duration
            bundle.putLong("duration", duration)
            val playbackPosition = player.currentTime().toLong() ?: 0L
            val playbackSpeed = player.mediaPlayer.playbackParams.speed ?: 0f
            val playbackStateBuilder = PlaybackStateCompat.Builder()
                .setState(state, playbackPosition, playbackSpeed)
                .setActiveQueueItemId(currentQueueItemId.toLong())
            playbackStateBuilder.setExtras(bundle)
            playbackStateBuilder.setState(state, playbackPosition, playbackSpeed)
            mediaSession.setPlaybackState(playbackStateBuilder.build())
        } catch (e: Exception) {
        }
    }

    private fun setMediaMetadata() {
        val ae = metadataBuilder(list[currentQueueItemId])
        val item = list[currentQueueItemId]
        Log.i(TAG, "item_icon_uri: ${item.description.iconUri}")
        Log.i(TAG, "item_media_uri: ${item.description.mediaUri}")
        Log.i(TAG, "icon: ${ae!!.build().description.iconUri}")
        Log.i(TAG, "media: ${ae.build().description.mediaUri}")
        mediaSession.setMetadata(
            ae.build()
        )
    }

    private fun refreshNotification() {
        Log.i(TAG, "refreshNotification: player.isPlaying = ${player.isPlaying()}")
        val builder = NotificationHelper.notificationBuilder(
            applicationContext,
            mediaSession,
            player.isPlaying()
        )


        startForeground(14881, builder.build())
//        nm.notify(14881, builder.build())
    }

    //
//    private fun refreshNotification() {
//        val isPlaying = mediaPlayer?.isPlaying ?: false
//        val playPauseIntent = if (isPlaying) {
//            Intent(applicationContext, MediaPlaybackService::class.java).setAction("pause")
//        } else Intent(applicationContext, MediaPlaybackService::class.java).setAction("play")
//        val nextIntent =
//            Intent(applicationContext, MediaPlaybackService::class.java).setAction("next")
//        val prevIntent =
//            Intent(applicationContext, MediaPlaybackService::class.java).setAction("previous")
//
//        val intent = packageManager
//            .getLaunchIntentForPackage(packageName)
//            ?.setPackage(null)
//            ?.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
//        val activityIntent =
//            PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//        val builder = NotificationCompat.Builder(
//            applicationContext,
//            getString(R.string.NOTIFICATION_CHANNEL_ID)
//        ).apply {
//            val mediaMetadata = mediaSession.controller.metadata
//
//            // Previous button
//            addAction(
//                NotificationCompat.Action(
//                    R.drawable.baseline_fast_rewind_24, getString(R.string.play),
//                    PendingIntent.getService(
//                        applicationContext,
//                        0,
//                        prevIntent,
//                        PendingIntent.FLAG_IMMUTABLE
//                    )
//                )
//            )
//
//            // Play/pause button
//            val playOrPause = if (isPlaying) R.drawable.pause
//            else R.drawable.play
//            addAction(
//                NotificationCompat.Action(
//                    playOrPause, getString(R.string.play),
//                    PendingIntent.getService(
//                        applicationContext,
//                        0,
//                        playPauseIntent,
//                        PendingIntent.FLAG_IMMUTABLE
//                    )
//                )
//            )
//
//            // Next button
//            addAction(
//                NotificationCompat.Action(
//                    R.drawable.ic_next, getString(R.string.play),
//                    PendingIntent.getService(
//                        applicationContext,
//                        0,
//                        nextIntent,
//                        PendingIntent.FLAG_IMMUTABLE
//                    )
//                )
//            )
//
//            setStyle(
//                androidx.media.app.NotificationCompat.MediaStyle()
//                    .setShowActionsInCompactView(0, 1, 2)
//                    .setMediaSession(mediaSessionCompat.sessionToken)
//            )
//
//            val smallIcon = if (isPlaying) R.drawable.play
//            else R.drawable.pause
//            setSmallIcon(smallIcon)
//
//            setContentIntent(activityIntent)
//
//            // Add the metadata for the currently playing track
//            setContentTitle(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_TITLE))
//            setContentText(mediaMetadata.getString(MediaMetadataCompat.METADATA_KEY_ARTIST))
//            setLargeIcon(mediaMetadata.getBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART))
//
//            // Make the transport controls visible on the lockscreen
//            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            priority = NotificationCompat.PRIORITY_DEFAULT
//        }
//        // Display the notification and place the service in the foreground
//        startForeground(1, builder.build())
//    }

    // нужно сделать чтобы поток и в нём обновлялось проигрываемое значение
    inner class PlayerPosition() : Runnable {
        override fun run() {
            try {
                val state = mediaSession.controller.playbackState.state
                setMediaPlaybackState(state)
            } catch (e: Exception) {
                e
            } finally {
                handler.postDelayed(this, 900)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()

        init()
    }

    private fun init() {
        initMediaSession()
        initPlayer()
        NotificationHelper.createChannelForMediaPlayerNotification(applicationContext)
        playerPositionRunnable.run()
        Thread(playerPositionRunnable).start()
    }

    private fun initMediaSession() {
        mediaSession = MediaSessionCompat(applicationContext, "MediaPlaybackService").apply {
            setFlags(
                MediaSessionCompat.FLAG_HANDLES_QUEUE_COMMANDS
            )
            setCallback(callback)
            setSessionToken(this.sessionToken)
            val builder = PlaybackStateCompat
                .Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                            PlaybackStateCompat.ACTION_PAUSE or
                            PlaybackStateCompat.ACTION_STOP or
                            PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                            PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                            PlaybackStateCompat.ACTION_PLAY_PAUSE
                )

            setPlaybackState(builder.build())

//            TODO("сделать юз кейс чтобы вызвать мейн активити")
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            setSessionActivity(
//                PendingIntent.getActivity(
//                    applicationContext,
//                    0,
//                    intent,
//                    PendingIntent.FLAG_IMMUTABLE
//                )
//            )
        }
    }

    private fun initPlayer() {
        player = Player(applicationContext)
        player.onCompletionListener = onCompletionListener
        player.onErrorListener = onErrorListener
    }

//    private fun initReceiver() {
//        noisyReceiver = NoisyReceiver(mediaSession)
//    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: on start com")
        intent?.action?.let {
            when (it) {
                "play" -> callback.onPlay()
                "pause" -> callback.onPause()
                "next" -> callback.onSkipToNext()
                "previous" -> callback.onSkipToPrevious()
                "clear" -> clearQueue()
            }
        }
        Log.i(TAG, "onStartCommand: 123")
        MediaButtonReceiver.handleIntent(mediaSession, intent)
        return super.onStartCommand(intent, flags, startId)

    }

    private fun clearQueue() {
        list.clear()
        mediaSession.setQueue(list)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(playerPositionRunnable)
        mediaSession.release()
        player.stop()
//        handler.removeCallbacks(playerPosition)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot {
        if (TextUtils.equals(packageName, clientPackageName)) {
            return BrowserRoot("mediaService", null)
        } else {
            return BrowserRoot("yabujin", null)
        }
    }


    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.sendResult(null)
    }


    companion object {
        const val TAG = "PLAYBACK_SERVICE"

        @Pashalko
        val NOTIFIC_CHANNEL_ID = 1488

        annotation class Pashalko
    }
}
