package com.example.music.service

import com.example.domain.model.Music

interface MusicPlayerController {
    val isPlaying: Boolean
    fun startPlayer()
    fun pausePlayer()
    fun stopPlayer()
    fun changeMediaItem(music: Music)
    fun playNext()
    fun playPrevious()
}