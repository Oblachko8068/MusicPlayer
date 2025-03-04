package com.example.domain.repository

import androidx.lifecycle.LiveData
import com.example.domain.model.Playlist

interface PlaylistRepository {

    fun getPlaylistList(): LiveData<List<Playlist>>

    suspend fun addNewPlaylist(playlist: Playlist)

    suspend fun updatePlaylist(playlist: Playlist)
}