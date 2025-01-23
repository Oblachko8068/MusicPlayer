package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.Playlist

interface PlaylistRepository {

    fun getPlaylistList(): LiveData<List<Playlist>>

    fun addNewPlaylist(playlistItem: Playlist)
}