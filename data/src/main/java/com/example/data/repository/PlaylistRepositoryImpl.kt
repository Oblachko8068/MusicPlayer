package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.data.model.fromPlaylistToPlaylistDbEntity
import com.example.data.room.PlaylistDao
import com.example.domain.model.Playlist
import com.example.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {

    override fun getPlaylistList(): LiveData<List<Playlist>> = playlistDao.getAllPlaylistList()
        .map { playlistDbEntities -> playlistDbEntities.map { it.toPlaylistDb() } }

    override fun addNewPlaylist(playlistItem: Playlist) {
        playlistDao.addNewPlaylist(playlistItem.fromPlaylistToPlaylistDbEntity())
    }
}