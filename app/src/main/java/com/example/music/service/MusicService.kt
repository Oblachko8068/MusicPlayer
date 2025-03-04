package com.example.music.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.Bitmap
import android.os.Binder
import android.os.IBinder
import androidx.annotation.OptIn
import androidx.core.app.NotificationCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.ui.PlayerNotificationManager
import com.example.domain.model.Music
import com.example.music.MainActivity
import com.example.music.R

class MusicService : Service(), MusicPlayerController {

    private val binder = MusicBinder()
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var mediaSession: MediaSession
    var musicList: List<Music> = emptyList()
    var currentMusicPosition: Int = 0
    private var currentMusic: Music? = null

    inner class MusicBinder : Binder() {
        val service: MusicService
            get() = this@MusicService
    }

    override fun onBind(intent: Intent): IBinder = binder

    override fun onCreate() {
        super.onCreate()
        initializePlayer()
        setupNotification()
        mediaSession = MediaSession.Builder(this, exoPlayer).build()
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun initializePlayer() {
        exoPlayer = ExoPlayer.Builder(this).build().apply {
            addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) playNext()
                }
            })
        }
    }

    @OptIn(UnstableApi::class)
    private fun setupNotification() {
        PlayerNotificationManager.Builder(this, NOTIFICATION_ID, CHANNEL_ID).apply {
            setMediaDescriptionAdapter(object : PlayerNotificationManager.MediaDescriptionAdapter {
                override fun getCurrentContentTitle(player: Player): CharSequence =
                    currentMusic?.title ?: "Unknown"

                override fun createCurrentContentIntent(player: Player): PendingIntent? =
                    PendingIntent.getActivity(
                        this@MusicService, 0,
                        Intent(this@MusicService, MainActivity::class.java),
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                    )

                override fun getCurrentContentText(player: Player): CharSequence? =
                    currentMusic?.artist ?: "Unknown Artist"

                override fun getCurrentLargeIcon(
                    player: Player,
                    callback: PlayerNotificationManager.BitmapCallback
                ): Bitmap? = null
            })
        }.build().apply {
            setPlayer(exoPlayer)
            setPriority(NotificationCompat.PRIORITY_LOW)
        }
    }

    private fun createNotification(): Notification {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Music Player",
            NotificationManager.IMPORTANCE_LOW
        ).apply { setShowBadge(false) }

        (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.icon_music_note)
            .setContentTitle(currentMusic?.title ?: "Music Player")
            .setContentText(currentMusic?.artist ?: "Unknown Artist")
            .build()
    }

    override val isPlaying: Boolean
        get() = exoPlayer.isPlaying

    override fun startPlayer() {
        exoPlayer.playWhenReady = true
    }

    override fun pausePlayer() {
        exoPlayer.playWhenReady = false
    }

    override fun stopPlayer() {
        exoPlayer.stop()
        stopSelf()
    }

    override fun changeMediaItem(music: Music) {
        currentMusic = music
        exoPlayer.setMediaItem(MediaItem.fromUri(music.path))
        exoPlayer.prepare()
    }

    fun playMusicAtPosition(position: Int) {
        if (position in musicList.indices) {
            currentMusicPosition = position
            val selectedMusic = musicList[position]
            changeMediaItem(selectedMusic)
            startPlayer()
        }
    }

    override fun playNext() {
        currentMusicPosition = (currentMusicPosition + 1) % musicList.size
        playMusicAtPosition(currentMusicPosition)
    }

    override fun playPrevious() {
        currentMusicPosition = (currentMusicPosition - 1 + musicList.size) % musicList.size
        playMusicAtPosition(currentMusicPosition)
    }

    override fun onDestroy() {
        exoPlayer.release()
        mediaSession.release()
        super.onDestroy()
    }

    companion object {
        const val CHANNEL_ID = "music_channel"
        const val NOTIFICATION_ID = 1001
    }
}