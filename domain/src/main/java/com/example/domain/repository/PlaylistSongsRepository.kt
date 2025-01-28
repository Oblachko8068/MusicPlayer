package com.example.domain.repository

import com.example.domain.model.PlaylistSongs

interface PlaylistSongsRepository {

    fun getAllSongsPlaylist(): List<PlaylistSongs>
}