package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.PlaylistSongs

interface PlaylistSongsRepository {

    fun getAllSongsFromPlaylist(playlistId: Int): LiveData<List<PlaylistSongs>>

    fun addNewSongToPlaylist(songItem: PlaylistSongs)
}