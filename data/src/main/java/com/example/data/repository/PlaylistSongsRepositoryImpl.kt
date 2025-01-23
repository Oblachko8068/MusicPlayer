package com.example.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.example.data.model.fromPlaylistSongsToPlaylistSongsDbEntity
import com.example.data.room.PlaylistSongsDao
import com.example.domain.model.PlaylistSongs
import com.example.domain.repository.PlaylistSongsRepository
import javax.inject.Inject

class PlaylistSongsRepositoryImpl @Inject constructor(
    private val playlistSongsDao: PlaylistSongsDao
) : PlaylistSongsRepository {

    override fun getAllSongsFromPlaylist(playlistId: Int): LiveData<List<PlaylistSongs>> =
        playlistSongsDao.getAllSongsFromPlaylist(playlistId)
            .map { playlistSongsDbEntities -> playlistSongsDbEntities.map { it.toPlaylistSongsDb() } }

    override fun addNewSongToPlaylist(songItem: PlaylistSongs) {
        playlistSongsDao.addNewSongToPlaylist(songItem.fromPlaylistSongsToPlaylistSongsDbEntity())
    }
}